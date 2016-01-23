(ns talk-post.core
  (:require [selmer.parser :as parser]))

(def generate-html
  (partial parser/render-file "talk_template.html"))

(defn generate-post [& {title :title :as talk}]
  {:content (generate-html talk)
   :title (parser/render "Interesting Talk: &quot;{{title}}&quot;" {:title title})})