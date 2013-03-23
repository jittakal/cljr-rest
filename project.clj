(defproject cljr-rest "0.1.0"
  :description "Clojure Project Sampe REST Style API"
  :url "http://jittakal.blogspot.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.0"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [org.clojure/tools.nrepl "0.2.2"]
                 [com.h2database/h2 "1.3.170"]]
  :main cljr-rest.core)
