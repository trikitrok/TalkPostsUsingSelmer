(ns talk-post.core
  (:require [selmer.parser :as parser]
            [clojure.string :as string]))

(def generate-html
  (partial parser/render-file "post_template.html"))

(defn generate-post [{title :title thing :thing :as talk}]
  {:content (generate-html talk)
   :title (parser/render "Interesting {{thing}}: &quot;{{title}}&quot;"
                         {:thing (string/capitalize thing) :title title})})

(defn generate-talk-post [& {:as talk}]
  (-> talk
      (merge {:verb "watched" :thing "talk"})
      generate-post))

(defn generate-paper-post [& {:as talk}]
  (-> talk
      (merge {:verb "read" :thing "paper"})
      generate-post))
