(ns ^{:author "Jitendra Takalkar"
      :doc "User table related database access functions"}
  cljr_rest.db.user
  (:require [clojure.java.jdbc :as sql]))

(def h2-db {:classname "org.h2.Driver" ; must be in classpath
            :subprotocol "h2"
            :subname "file:/home/jittakal/Data/cljr-rest"
            ; Any additional keys are passed to the driver
            ; as driver-specific properties.
            :user     "sa"
            :password ""})