(ns gitclj.interface
  (:require [gitclj.gitlab :as gitlab]
            [clojure.pprint :refer [print-table]])
  (:gen-class))

(defn help [& args]
  (->> ["This is a CLI application to handle some necessities in Gitlab/Github."
        ""
        "Usage: lein run [options] action"
        ""
        "Options:"
        "-g --gitlab ACTION ARGUMENT"
        ""
        "Actions:"
        "  groups                    List all groups in your GitLab"
        "  projects <group-id>       List all projects inside a gitlab group"
        "  clone-all <directory>     Clone all your repositories inside the <directory>"
        ""
        "Please, contribute to improve the functionalitites =]"]
       (clojure.string/join "\n")
       (println)))

(defn gitlab-actions
  [[action arg]]
  (cond (= action "groups")
        (print-table [:id :name :url] (->> (gitlab/gitlab-groups)
                                           (map #(zipmap [:id :name :url] %))))
        (= action "projects")
        (let [header [:id :name :qualified-name :url]
              parser (partial zipmap header)]
          (if (nil? arg)
            (print-table header (->> (gitlab/get-all-projects)
                                     (map parser)))
            (print-table header (->> (gitlab/get-projects-from-group arg)
                                     (map parser)))))
        (= action "clone-all")
        (if (nil? arg)
          (gitlab/clone-all-repos "/Users/wandersonferreira/gitlab")
          (gitlab/clone-all-repos arg))
        (= action "commit-history")
        (print-table [:name :num-commits] (->> (gitlab/commit-history arg)
                                               (map #(zipmap [:name :num-commits] %))))
        :else
        (println "This action is not valid! Please, consult --ajuda")))
