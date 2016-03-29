(ns talk-post.core
  (:require
    [selmer.parser :as parser]
    [clojure.string :as string]
    [hiccup.core]
    [hiccup.element]))

(defn- format-authors-links [authors-links]
  (clojure.string/replace
    (str
      (clojure.string/join
        ", " (cons (first authors-links) (butlast (rest authors-links))))
      (if-let [last-link (last (rest authors-links))]
        (str " and " last-link)
        ""))
    #"\"" "'"))

(defn- author-links [authors]
  (for [{:keys [name url]} authors]
    (hiccup.core/html (hiccup.element/link-to url name))))

(defn- generate-title [{:keys [title thing]}]
  (parser/render
    "Interesting {{thing}}: &quot;{{title}}&quot;"
    {:thing (string/capitalize thing) :title title}))

(defmulti ^:private generate-content :thing)

(defmethod ^:private generate-content "podcast" [data]
  (parser/render-file "podcast_post_template.html" data))

(defmethod ^:private generate-content :default [data]
  (parser/render-file
    "post_template.html"
    (assoc data :authors-links (format-authors-links (author-links (:authors data))))))

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
