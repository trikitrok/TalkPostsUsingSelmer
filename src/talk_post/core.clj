(ns talk-post.core
  (:require [selmer.parser :as parser]
            [clojure.string :as string]))

(def generate-html
  (partial parser/render-file "post_template.html"))

(defn generate-title [{:keys [title thing]}]
  (parser/render
    "Interesting {{thing}}: &quot;{{title}}&quot;"
    {:thing (string/capitalize thing) :title title}))

(defn generate-post [post-data]
  {:content (generate-html post-data)
   :title (generate-title post-data)})

(defn generate-talk-post [& {:as talk-data}]
  (-> talk-data
      (merge {:verb "watched" :thing "talk"})
      generate-post))

(defn generate-paper-post [& {:as paper-data}]
  (-> paper-data
      (merge {:verb "read" :thing "paper"})
      generate-post))
