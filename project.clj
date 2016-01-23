(defproject talk-post "0.0.1-SNAPSHOT"
  :description "Playing with Selmer to create blog posts"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [selmer "1.0.0"]]
  :profiles {:dev {:dependencies [[midje "1.7.0"]]}
             :midje {}})
