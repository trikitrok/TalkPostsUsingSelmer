(ns talk-post.core-test
  (:require [midje.sweet :refer :all]
            [talk-post.core :refer :all]))

(facts
  "about posts"

  (fact
    "it generates their html"

    (generate-html
      {:verb "watched"
       :thing "talk"
       :adjective "wonderful"
       :author-url "http://nestorsalceda.com/blog/"
       :author-name "Nestor Salceda"
       :url "http://www.decharlas.uji.es/es/despliegue-continuo-docker-ansible"
       :title "Despliegue continuo con Docker y Ansible"})
    => "I've just watched this wonderful talk by <a href='http://nestorsalceda.com/blog/'>Nestor Salceda</a>:\n<ul>\n    <li>\n        <a href='http://www.decharlas.uji.es/es/despliegue-continuo-docker-ansible'>Despliegue continuo con Docker y Ansible</a>\n    </li>\n</ul>")

  (fact
    "it generates a post"

    (generate-post
      {:verb "watched"
       :thing "talk"
       :adjective "wonderful"
       :author-url "http://nestorsalceda.com/blog/"
       :author-name "Nestor Salceda"
       :url "http://www.decharlas.uji.es/es/despliegue-continuo-docker-ansible"
       :title "Despliegue continuo con Docker y Ansible"})
    => {:title "Interesting Talk: &quot;Despliegue continuo con Docker y Ansible&quot;"
        :content "I've just watched this wonderful talk by <a href='http://nestorsalceda.com/blog/'>Nestor Salceda</a>:\n<ul>\n    <li>\n        <a href='http://www.decharlas.uji.es/es/despliegue-continuo-docker-ansible'>Despliegue continuo con Docker y Ansible</a>\n    </li>\n</ul>"})

  (fact
    "it generates a watched talk post"

    (generate-talk-post
      :adjective "wonderful"
      :author-url "http://nestorsalceda.com/blog/"
      :author-name "Nestor Salceda"
      :url "http://www.decharlas.uji.es/es/despliegue-continuo-docker-ansible"
      :title "Despliegue continuo con Docker y Ansible")
    => {:title "Interesting Talk: &quot;Despliegue continuo con Docker y Ansible&quot;"
        :content "I've just watched this wonderful talk by <a href='http://nestorsalceda.com/blog/'>Nestor Salceda</a>:\n<ul>\n    <li>\n        <a href='http://www.decharlas.uji.es/es/despliegue-continuo-docker-ansible'>Despliegue continuo con Docker y Ansible</a>\n    </li>\n</ul>"})

  (fact
    "it generates a read paper post"

      (generate-paper-post
        :adjective "great"
        :author-url "https://en.wikipedia.org/wiki/Peter_Naur"
        :author-name "Peter Naur"
        :url "http://pages.cs.wisc.edu/~remzi/Naur.pdf"
        :title "Programming as Theory Building")
      => {:title "Interesting Paper: &quot;Programming as Theory Building&quot;"
          :content "I've just read this great paper by <a href='https://en.wikipedia.org/wiki/Peter_Naur'>Peter Naur</a>:\n<ul>\n    <li>\n        <a href='http://pages.cs.wisc.edu/~remzi/Naur.pdf'>Programming as Theory Building</a>\n    </li>\n</ul>"}))
