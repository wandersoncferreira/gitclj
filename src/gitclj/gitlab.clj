(ns gitclj.gitlab
  (:require [cheshire.core :as json]
            [clj-http.client :as client]
            [clojure.java.shell :refer [sh with-sh-dir]]
            [environ.core :refer [env]]))

(def base-url "http://gitlab.com/api/v4/")
;;(def folder-dest "/Users/wandersonferreira/gitlab")

(defn gitlab-post
  [url]
  (:body (client/get url {:headers {"PRIVATE-TOKEN" (:apikey env)}
                          :as :json})))
(defn gitlab-groups
  []
  (let [group-url (str base-url "groups")
        resp (gitlab-post group-url)]
    (map (juxt :id :full_name :web_url) resp)))

(defn get-projects-from-group
  [group-id]
  (let [get-url (str base-url "groups/" group-id "/projects")
        resp (gitlab-post get-url)]
    (map (juxt :id :name :name_with_namespace #(clojure.string/replace (:http_url_to_repo %)
                                                                       #"https://gitlab.com/"
                                                                       "git@gitlab.com:")) resp)))

(defn get-all-projects
  [& {:keys [group-ids] :or {group-ids (map first (gitlab-groups))}}]
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
