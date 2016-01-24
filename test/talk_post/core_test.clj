(ns talk-post.core-test
  (:require [midje.sweet :refer :all]
            [talk-post.core :refer :all]))

(facts
  "about Talk posts"

  (fact
    "it generates their html"

    (generate-html
      {:adjective "wonderful"
      :author-url "http://nestorsalceda.com/blog/"
      :author-name "Nestor Salceda"
      :url "http://www.decharlas.uji.es/es/despliegue-continuo-docker-ansible"
      :title "Despliegue continuo con Docker y Ansible"})
    => "I've just watched this wonderful talk by <a href='http://nestorsalceda.com/blog/'>Nestor Salceda</a>:\n<ul>\n    <li>\n        <a href='http://www.decharlas.uji.es/es/despliegue-continuo-docker-ansible'>Despliegue continuo con Docker y Ansible</a>\n    </li>\n</ul>")

  (fact
    "its generates its post"

    (generate-post
      :adjective "wonderful"
      :author-url "http://nestorsalceda.com/blog/"
      :author-name "Nestor Salceda"
      :url "http://www.decharlas.uji.es/es/despliegue-continuo-docker-ansible"
      :title "Despliegue continuo con Docker y Ansible")
    => {:title "Interesting Talk: &quot;Despliegue continuo con Docker y Ansible&quot;"
        :content "I've just watched this wonderful talk by <a href='http://nestorsalceda.com/blog/'>Nestor Salceda</a>:\n<ul>\n    <li>\n        <a href='http://www.decharlas.uji.es/es/despliegue-continuo-docker-ansible'>Despliegue continuo con Docker y Ansible</a>\n    </li>\n</ul>"}))
