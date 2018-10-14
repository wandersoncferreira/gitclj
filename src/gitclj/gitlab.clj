(ns gitclj.gitlab
  (:require [cheshire.core :as json]
            [clj-http.client :as client]
            [clojure.java.shell :refer [sh with-sh-dir]]
            [environ.core :refer [env]]))

(def base-url "http://gitlab.com/api/v4/")
;;(def folder-dest "/Users/wandersonferreira/gitlab")

(defn gitlab-post
  [url]
  (let [resp (client/get url {:headers {"PRIVATE-TOKEN" (:apikey env)}
                              :as :json})]
    (:body resp)))

(defn gitlab-groups
  []
  (let [group-url (str base-url "groups")
        resp (gitlab-post group-url)]
    (map (juxt :id :full_name :web_url) resp)))

(defn get-projects-from-group
  [group-id & {:keys [per-page] :or {per-page 150}}]
  (let [get-url (str base-url "groups/" group-id "/projects" "?per_page=" per-page)
        resp (gitlab-post get-url)]
    (map (juxt :id :name :name_with_namespace #(clojure.string/replace (:http_url_to_repo %)
                                                                       #"https://gitlab.com/"
                                                                       "git@gitlab.com:")) resp)))

(defn get-all-projects
  [& {:keys [group-ids] :or {group-ids (map first (gitlab-groups))}}]
  (println (str "# groups: " (count group-ids)))
  (->> group-ids
       (map #(future (get-projects-from-group %)))
       doall
       (map deref)
       (apply concat)))

(defn clone-all-repos [folder-dest]
  (->> (get-all-projects)
       (map #(nth % 3))
       (map #(str "git clone " (str "\"" % "\"")))
       (map #(future (sh "sh" "-c" % :dir folder-dest)))
       doall
       (map deref)))

(defn amount-of-commit-repo
  [username repo-id & {:keys [per-page] :or {per-page 150}}]
  (loop [page 1
         final 0]
    (let [url-resp (str base-url "/projects/" repo-id "/repository/commits?all=true&page=" page "&per_page=" per-page)
          resp (gitlab-post url-resp)]
      (if (empty? resp)
        final
        (recur (+ page 1) (+ final (->> resp
                                        (map :author_name)
                                        (filter #(re-find (re-pattern username) %))
                                        count)))))))
(defn commit-history
  [username]
  (let [projects (get-all-projects)
        func (partial amount-of-commit-repo username)
        jx (juxt second #(func (first %)))]
    (println (str "# repositories: " (count projects)))
    (->> projects
         (map #(future (jx %)))
         doall
         (map deref)
         (filter #(> (second %) 0))
         (sort-by second)
         reverse)))
