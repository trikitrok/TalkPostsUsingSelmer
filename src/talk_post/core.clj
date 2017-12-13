(ns talk-post.core
  (:require
    [selmer.parser :as parser]
    [clojure.string :as string]
    [hiccup.core]
    [hiccup.element]))

(defn- join-people-links [people-links]
  (str
    (clojure.string/join
      ", "
      (cons (first people-links)
            (butlast (rest people-links))))
    (if-let [last-link (last (rest people-links))]
      (str " and " last-link)
      "")))

(defn- format-people-links [people-links]
  (->> people-links
       (map #(clojure.string/replace % #"\"" "'"))
       join-people-links))

(defn- people-links [people]
  (for [{:keys [name url]} people]
    (hiccup.core/html
      (hiccup.element/link-to url name))))

(defn- generate-title [{:keys [title thing]}]
  (parser/render
    "Interesting {{thing}}: &quot;{{title}}&quot;"
    {:thing (string/capitalize thing) :title title}))

(defn- render-post-template [data]
  (let [author-links (format-people-links (people-links (:authors data)))]
    (parser/render-file
      "post_template.html"
      (assoc data :authors-links author-links))))

(defmulti ^:private generate-content :thing)

(defmethod ^:private generate-content "podcast" [data]
  (parser/render-file "podcast_post_template.html"
                      (merge data {:verb "listened to"
                                   :preposition "with"})))

(defmethod ^:private generate-content "webcast" [data]
  (parser/render-file "podcast_post_template.html"
                      (merge data {:verb "watched"
                                   :preposition "with"})))

(defmethod ^:private generate-content "paper" [data]
  (render-post-template (merge data {:verb "read"
                                     :preposition "by"})))

(defmethod ^:private generate-content "talk" [data]
  (render-post-template (merge data {:verb "watched"
                                     :preposition "by"})))

(defmethod ^:private generate-content "interview" [data]
  (render-post-template (merge data {:verb "watched"
                                     :preposition "with"})))

(defmethod ^:private generate-content "panel" [data]
  (render-post-template (merge data {:thing "panel discussion"
                                     :verb "watched"
                                     :preposition "with"})))

(defn generate-post [& {:as post-data}]
  {:content (generate-content post-data)
   :title (generate-title post-data)})
