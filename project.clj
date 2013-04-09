(defproject cljr-rest "0.1.0"
  :description "Clojure Project Sampe REST Style API"
  :url "http://jittakal.blogspot.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.logging "0.2.6"]
                 [compojure "1.1.5"]
                 [ring/ring-json "0.2.0"]                 
                 [org.clojure/java.jdbc "0.2.3"]
                 [c3p0/c3p0 "0.9.1.2"]
                 [org.clojure/tools.nrepl "0.2.2"]
                 [com.h2database/h2 "1.3.170"]
                 [cheshire "5.1.0"]]
  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler cljr-rest.core/app}
  :profiles {:dev {:dependencies [[ring-mock "0.1.3"]]}})
;;  :main cljr-rest.core)
