(ns talk-post.core
  (:require [selmer.parser :as parser]
            [clojure.string :as string]))

(defn- generate-title [{:keys [title thing]}]
  (parser/render
    "Interesting {{thing}}: &quot;{{title}}&quot;"
    {:thing (string/capitalize thing) :title title}))

(defmulti ^:private generate-content :thing)

(defmethod ^:private generate-content "paper" [data]
  (parser/render-file "post_template.html" data))

(defmethod ^:private generate-content "talk" [data]
  (parser/render-file "post_template.html" data))

(defmethod ^:private generate-content "podcast" [data]
  (parser/render-file "podcast_post_template.html" data))

(defn- generate-post [post-data]
  {:content (generate-content post-data)
   :title (generate-title post-data)})

(defn generate-talk-post [& {:as talk-data}]
  (-> talk-data
      (merge {:verb "watched" :thing "talk"})
      generate-post))

(defn generate-paper-post [& {:as paper-data}]
  (-> paper-data
      (merge {:verb "read" :thing "paper"})
      generate-post))

(defn generate-podcast-post [& {:as podcast-data}]
  (-> podcast-data
      (merge {:thing "podcast"})
      generate-post))
