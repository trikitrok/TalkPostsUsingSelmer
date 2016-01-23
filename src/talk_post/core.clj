(ns talk-post.core
  (:require [selmer.parser :as parser]))

(defn generate-html [talk]
  (parser/render-file "talk_template.html" talk))
