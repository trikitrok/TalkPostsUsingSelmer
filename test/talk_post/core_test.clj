(ns talk-post.core-test
  (:require [midje.sweet :refer :all]
            [talk-post.core :refer :all]))

(defn without-new-lines [s]
  (clojure.string/replace s #"\n" ""))

(facts
  "about posts"

  (fact
    "it generates a watched talk post"

    (let
      [post (generate-talk-post
              :adjective "wonderful"
              :authors [{:url "http://nestorsalceda.com/blog/"
                         :name "Nestor Salceda"}]
              :url "http://www.decharlas.uji.es/es/despliegue-continuo-docker-ansible"
              :title "Despliegue continuo con Docker y Ansible")]
      (:title post) => "Interesting Talk: &quot;Despliegue continuo con Docker y Ansible&quot;"
      (without-new-lines (:content post)) => "I've just watched this wonderful talk by<a href='http://nestorsalceda.com/blog/'>Nestor Salceda</a>:<ul>    <li>        <a href='http://www.decharlas.uji.es/es/despliegue-continuo-docker-ansible'>Despliegue continuo con Docker y Ansible</a>    </li></ul>"))

  (fact
    "it generates a read paper post"

    (let [post (generate-paper-post
                 :adjective "great"
                 :authors [{:url "https://en.wikipedia.org/wiki/Peter_Naur"
                            :name "Peter Naur"}]
                 :url "http://pages.cs.wisc.edu/~remzi/Naur.pdf"
                 :title "Programming as Theory Building")]
      (:title post) =>"Interesting Paper: &quot;Programming as Theory Building&quot;"
      (without-new-lines (:content post)) => "I've just read this great paper by<a href='https://en.wikipedia.org/wiki/Peter_Naur'>Peter Naur</a>:<ul>    <li>        <a href='http://pages.cs.wisc.edu/~remzi/Naur.pdf'>Programming as Theory Building</a>    </li></ul>"))

  (fact
    "it generates a listened to podcast post"

    (generate-podcast-post
      :adjective "great"
      :all-podcasts-url "https://devchat.tv/ruby-rogues"
      :all-podcasts-name "Ruby Rogues"
      :topic "antipatterns in Ruby"
      :url "https://devchat.tv/ruby-rogues/032-rr-ruby-antipatterns"
      :title "Ruby Antipatterns")
    => {:title "Interesting Podcast: &quot;Ruby Antipatterns&quot;"
        :content "I've just listened to this great\n<a href='https://devchat.tv/ruby-rogues'>Ruby Rogues</a> podcast\n\n\ntalking about antipatterns in Ruby:\n\n<ul>\n    <li>\n        <a href='https://devchat.tv/ruby-rogues/032-rr-ruby-antipatterns'>Ruby Antipatterns</a>\n    </li>\n</ul>"})

  (generate-podcast-post
    :adjective "great"
    :all-podcasts-url "https://devchat.tv/ruby-rogues"
    :all-podcasts-name "Ruby Rogues"
    :topic "TDD and testing"
    :guest-name "Noel Rappin"
    :guest-url "http://www.noelrappin.com"
    :url "https://devchat.tv/ruby-rogues/185-rr-rails-4-test-prescriptions-with-noel-rappin"
    :title "Rails 4 Test Prescriptions with Noel Rappin")
  => {:title "Interesting Podcast: &quot;Rails 4 Test Prescriptions with Noel Rappin&quot;"
      :content "I've just listened to this great\n<a href='https://devchat.tv/ruby-rogues'>Ruby Rogues</a> podcast\n\nwith <a href='http://www.noelrappin.com'>Noel Rappin</a>\n\n\ntalking about TDD and testing:\n\n<ul>\n    <li>\n        <a href='https://devchat.tv/ruby-rogues/185-rr-rails-4-test-prescriptions-with-noel-rappin'>Rails 4 Test Prescriptions with Noel Rappin</a>\n    </li>\n</ul>"})
