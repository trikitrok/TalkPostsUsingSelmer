(ns talk-post.core-test
  (:require
    [midje.sweet :refer :all]
    [talk-post.core :refer :all]))

(defn without-new-lines-and-redundant-spaces [s]
  (-> s
      (clojure.string/replace #"\n" "")
      (clojure.string/replace #"\s+" " ")))

(facts
  "about posts"

  (fact
    "it generates a watched talk post"
    (let
      [post (generate-post
              :thing "talk"
              :adjective "wonderful"
              :authors [{:url "http://nestorsalceda.com/blog/"
                         :name "Nestor Salceda"}]
              :url "http://www.decharlas.uji.es/es/despliegue-continuo-docker-ansible"
              :title "Despliegue continuo con Docker y Ansible")]
      (:title post) => "Interesting Talk: &quot;Despliegue continuo con Docker y Ansible&quot;"
      (without-new-lines-and-redundant-spaces
        (:content post)) => "I've just watched this wonderful talk by <a href='http://nestorsalceda.com/blog/'>Nestor Salceda</a><ul> <li> <a href='http://www.decharlas.uji.es/es/despliegue-continuo-docker-ansible'>Despliegue continuo con Docker y Ansible</a> </li></ul>"))

  (fact
    "it generates a watched talk post with several authors"
    (let
      [post (generate-post
              :thing "talk"
              :adjective "wonderful"
              :authors [{:url "http://swannodette.github.io/"
                         :name "David Nolen"}
                        {:url "http://kovasboguta.com/"
                         :name "Kovas Boguta"}]
              :url "http://www.infoq.com/presentations/domain-driven-architecture"
              :title "Demand-Driven Architecture")]
      (:title post) => "Interesting Talk: &quot;Demand-Driven Architecture&quot;"
      (without-new-lines-and-redundant-spaces
        (:content post)) => "I've just watched this wonderful talk by <a href='http://swannodette.github.io/'>David Nolen</a> and <a href='http://kovasboguta.com/'>Kovas Boguta</a><ul> <li> <a href='http://www.infoq.com/presentations/domain-driven-architecture'>Demand-Driven Architecture</a> </li></ul>"))

  (fact
    "it generates a read paper post"
    (let [post (generate-post
                 :thing "paper"
                 :adjective "great"
                 :authors [{:url "https://en.wikipedia.org/wiki/Peter_Naur"
                            :name "Peter Naur"}]
                 :url "http://pages.cs.wisc.edu/~remzi/Naur.pdf"
                 :title "Programming as Theory Building")]
      (:title post) => "Interesting Paper: &quot;Programming as Theory Building&quot;"
      (without-new-lines-and-redundant-spaces
        (:content post)) => "I've just read this great paper by <a href='https://en.wikipedia.org/wiki/Peter_Naur'>Peter Naur</a><ul> <li> <a href='http://pages.cs.wisc.edu/~remzi/Naur.pdf'>Programming as Theory Building</a> </li></ul>"))

  (fact
    "it generates a listened to podcast post"
    (generate-post
      :thing "podcast"
      :adjective "great"
      :all-url "https://devchat.tv/ruby-rogues"
      :all-name "Ruby Rogues"
      :topic "antipatterns in Ruby"
      :url "https://devchat.tv/ruby-rogues/032-rr-ruby-antipatterns"
      :title "Ruby Antipatterns")
    => {:title "Interesting Podcast: &quot;Ruby Antipatterns&quot;"
        :content "I've just listened to this great\n<a href='https://devchat.tv/ruby-rogues'>Ruby Rogues</a> podcast\n\n\ntalking about antipatterns in Ruby:\n\n<ul>\n    <li>\n        <a href='https://devchat.tv/ruby-rogues/032-rr-ruby-antipatterns'>Ruby Antipatterns</a>\n    </li>\n</ul>"})

  (fact
    "it generates a listened to podcast with guest post"
    (generate-post
      :thing "podcast"
      :adjective "great"
      :all-url "https://devchat.tv/ruby-rogues"
      :all-name "Ruby Rogues"
      :topic "TDD and testing"
      :guest-name "Noel Rappin"
      :guest-url "http://www.noelrappin.com"
      :url "https://devchat.tv/ruby-rogues/185-rr-rails-4-test-prescriptions-with-noel-rappin"
      :title "Rails 4 Test Prescriptions with Noel Rappin")
    => {:title "Interesting Podcast: &quot;Rails 4 Test Prescriptions with Noel Rappin&quot;"
        :content "I've just listened to this great\n<a href='https://devchat.tv/ruby-rogues'>Ruby Rogues</a> podcast\n\nwith <a href='http://www.noelrappin.com'>Noel Rappin</a>\n\n\ntalking about TDD and testing:\n\n<ul>\n    <li>\n        <a href='https://devchat.tv/ruby-rogues/185-rr-rails-4-test-prescriptions-with-noel-rappin'>Rails 4 Test Prescriptions with Noel Rappin</a>\n    </li>\n</ul>"})

  (fact
    "it generates a watched webcast with guest post"
    (generate-post
      :thing "webcast"
      :adjective "great"
      :all-url "https://devchat.tv/ruby-rogues"
      :all-name "Ruby Rogues"
      :topic "TDD and testing"
      :guest-name "Noel Rappin"
      :guest-url "http://www.noelrappin.com"
      :url "https://devchat.tv/ruby-rogues/185-rr-rails-4-test-prescriptions-with-noel-rappin"
      :title "Rails 4 Test Prescriptions with Noel Rappin")
    => {:title "Interesting Webcast: &quot;Rails 4 Test Prescriptions with Noel Rappin&quot;"
        :content "I've just watched this great\n<a href='https://devchat.tv/ruby-rogues'>Ruby Rogues</a> webcast\n\nwith <a href='http://www.noelrappin.com'>Noel Rappin</a>\n\n\ntalking about TDD and testing:\n\n<ul>\n    <li>\n        <a href='https://devchat.tv/ruby-rogues/185-rr-rails-4-test-prescriptions-with-noel-rappin'>Rails 4 Test Prescriptions with Noel Rappin</a>\n    </li>\n</ul>"})

  (fact "it generates a watched interview post"
    (generate-post
      :thing "interview"
      :adjective "wonderful"
      :authors [{:url "http://www.natpryce.com/"
                 :name "Nat Pryce"}]
      :url "https://www.youtube.com/watch?v=VDWZ85Cggn0"
      :title "TDD is evolving")
    => {:content "I've just watched this wonderful interview with <a href='http://www.natpryce.com/'>Nat Pryce</a>\n<ul>\n    <li>\n        <a href='https://www.youtube.com/watch?v=VDWZ85Cggn0'>TDD is evolving</a>\n    </li>\n</ul>", :title "Interesting Interview: &quot;TDD is evolving&quot;"})

  (fact
    "it generates a watched panel post"
    (let
      [post (generate-post
              :thing "panel"
              :adjective "very interesting"
              :authors [{:url "https://yogthos.net/"
                         :name "Dmitri Sotnikov"}
                        {:url "https://github.com/kamituel"
                         :name "Kamil Leszczuk"}
                        {:url "https://twitter.com/matthiasnehlsen?lang=en"
                         :name "Matthias Nehlsen"}
                        {:url "https://twitter.com/ohpauleez?lang=en"
                         :name "Paul deGrandis"}
                        {:url "https://twitter.com/ordnungswprog?lang=en"
                         :name "Philipp Meier"}]
              :url "https://www.youtube.com/watch?v=JKoaG4kSyxs"
              :title "Clojure Remote Panel: Web Development in Clojure")]
      (:title post) => "Interesting Panel: &quot;Clojure Remote Panel: Web Development in Clojure&quot;"
      (without-new-lines-and-redundant-spaces
        (:content post)) => "I've just watched this very interesting panel discussion with <a href='https://yogthos.net/'>Dmitri Sotnikov</a>, <a href='https://github.com/kamituel'>Kamil Leszczuk</a>, <a href='https://twitter.com/matthiasnehlsen?lang=en'>Matthias Nehlsen</a>, <a href='https://twitter.com/ohpauleez?lang=en'>Paul deGrandis</a> and <a href='https://twitter.com/ordnungswprog?lang=en'>Philipp Meier</a><ul> <li> <a href='https://www.youtube.com/watch?v=JKoaG4kSyxs'>Clojure Remote Panel: Web Development in Clojure</a> </li></ul>"))

  (fact
    "it generates a watched screencast post"
    (let
      [post (generate-post
              :thing "screencast"
              :adjective "great"
              :authors [{:url "https://twitter.com/emilybache?lang=en"
                         :name "Emily Bache"}]
              :url "https://www.youtube.com/watch?v=zyM2Ep28ED8"
              :title "Introducing the Gilded Rose kata and writing test cases using Approval Tests")]
      (:title post) => "Interesting Screencast: &quot;Introducing the Gilded Rose kata and writing test cases using Approval Tests&quot;"
      (without-new-lines-and-redundant-spaces
        (:content post)) => "I've just watched this great screencast by <a href='https://twitter.com/emilybache?lang=en'>Emily Bache</a><ul> <li> <a href='https://www.youtube.com/watch?v=zyM2Ep28ED8'>Introducing the Gilded Rose kata and writing test cases using Approval Tests</a> </li></ul>"))

  (fact
    "it generates a watched interview post"
    (let
      [post (generate-post
              :thing "conversation"
              :adjective "very interesting"
              :authors [{:url "https://en.wikipedia.org/wiki/Brian_Eno"
                         :name "Brian Eno"}
                        {:url "https://en.wikipedia.org/wiki/Yanis_Varoufakis"
                         :name "Yanis Varoufakis"}]
              :all-url "https://www.youtube.com/channel/UCnMk-6Brd8rVEKWSWkwsWUg"
              :all-name "DiEM25 TV"
              :url "https://www.youtube.com/watch?v=J7ei1-rYHMU"
              :title "Reflecting on our Post-Virus World")]
      (:title post) => "Interesting Conversation: &quot;Reflecting on our Post-Virus World&quot;"
      (without-new-lines-and-redundant-spaces
        (:content post)) => "I've just watched this very interesting conversation between <a href='https://en.wikipedia.org/wiki/Brian_Eno'>Brian Eno</a> and <a href='https://en.wikipedia.org/wiki/Yanis_Varoufakis'>Yanis Varoufakis</a> on <a href='https://www.youtube.com/channel/UCnMk-6Brd8rVEKWSWkwsWUg'>DiEM25 TV</a><ul> <li> <a href='https://www.youtube.com/watch?v=J7ei1-rYHMU'>Reflecting on our Post-Virus World</a> </li></ul>"))

  (fact
    "it generates a watched documentary film"
    (let
      [post (generate-post
              :thing "documentary film"
              :adjective "great"
              :authors [{:url "https://en.wikipedia.org/wiki/Iris_Zaki"
                         :name "Iris Zaki"}]
              :url "https://www.youtube.com/watch?v=Eac1l1ozfLc"
              :title "What It’s Like to Grow Up in an Israeli Settlement")]
      (:title post) => "Interesting Documentary film: &quot;What It’s Like to Grow Up in an Israeli Settlement&quot;"
      (without-new-lines-and-redundant-spaces
        (:content post)) => "I've just watched this great documentary film by <a href='https://en.wikipedia.org/wiki/Iris_Zaki'>Iris Zaki</a><ul> <li> <a href='https://www.youtube.com/watch?v=Eac1l1ozfLc'>What It’s Like to Grow Up in an Israeli Settlement</a> </li></ul>"))

  (fact "it adds authors even when no url is given"
        (generate-post
          :thing "interview"
          :adjective "short interesting"
          :authors [{:name "Jenny Quillen"}]
          :url "https://www.youtube.com/watch?v=ad5XAPgKJoM&app=desktop"
          :title "History from A Pattern Language to the Nature of Order")
        => {:content "I've just watched this short interesting interview with Jenny Quillen\n<ul>\n    <li>\n        <a href='https://www.youtube.com/watch?v=ad5XAPgKJoM&amp;app=desktop'>History from A Pattern Language to the Nature of Order</a>\n    </li>\n</ul>"
            :title "Interesting Interview: &quot;History from A Pattern Language to the Nature of Order&quot;"}))
