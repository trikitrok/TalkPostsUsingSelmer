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
    (if (nil? url)
      name
      (hiccup.core/html
        (hiccup.element/link-to url name)))))

(defn- generate-title [{:keys [title thing]}]
  (parser/render
    "Interesting {{thing}}: &quot;{{title}}&quot;"
    {:thing (string/capitalize thing) :title title}))

(def ^:private post-type-data-variations
  {"paper" {:verb "read"
            :preposition "by"}
   "talk" {:verb "watched"
           :preposition "by"}
   "screencast" {:verb "watched"
                 :preposition "by"}
   "interview" {:verb "watched"
                :preposition "with"}
   "documentary film" {:verb "watched"
                       :preposition "by"}
   "panel" {:thing "panel discussion"
            :verb "watched"
            :preposition "with"}
   "podcast" {:verb "listened to"
              :preposition "with"}
   "webcast" {:verb "watched"
              :preposition "with"}
   "conversation" {:verb "watched"
                   :preposition "between"}})

(defn- get-template [{:keys [thing]}]
  (if (contains? #{"podcast" "webcast"} thing)
    "podcast_post_template.html"
    "post_template.html"))

(defn- extract-authors [post]
  (format-people-links (people-links (:authors post))))

(defn- remove-authors-data-if-no-authors [post]
  (if (empty? (:authors-links post))
    (dissoc (assoc post :preposition "") :authors-links)
    post))

(defn- fill-template [{:keys [thing] :as post}]
  (-> post
      (merge (get post-type-data-variations thing))
      (assoc :authors-links (extract-authors post))
      (remove-authors-data-if-no-authors)))

(defn- generate-content [post]
  (parser/render-file
    (get-template post)
    (fill-template post)))

(defn generate-post [& {:as post}]
  {:content (generate-content post)
   :title (generate-title post)})

(defn generate! [& {:as post}]
  (let [post (apply generate-post (->> post vec (apply concat)))]
    (println (:title post))
    (println)
    (println (:content post))))

