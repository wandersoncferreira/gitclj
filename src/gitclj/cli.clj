(ns gitclj.cli
  (:require [clojure.tools.cli :refer [parse-opts]]
            [gitclj.interface :as git-interface]))


(def cli-options
  [["-g" "--gitlab ACTION" "Action to perform at Gitlab repository. e.g. clone"
    :parse-fn (fn [action]
                (-> action
                    (clojure.string/split #",")
                    (git-interface/gitlab-actions)))]
   ["-a" "--ajuda" "How to use the system."
    :parse-fn #(git-interface/help %)]])

(defn- main [& args]
  (parse-opts args cli-options :in-order true))

