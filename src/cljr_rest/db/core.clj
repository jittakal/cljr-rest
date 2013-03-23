(ns ^{:author "Jitendra Takalkar"
      :doc "Database core or common functions"}
  cljr-rest.db.core
  (:require [clojure.java.jdbc :as sql]))

(def db {:classname "org.h2.Driver" ; must be in classpath
            :subprotocol "h2"
            :subname "file:/home/jittakal/Data/cljr-rest"
            ; Any additional keys are passed to the driver
            ; as driver-specific properties.
            :user     "sa"
            :password ""})

(defn drop-blogs
  "Drop blogs table"
  []
  (sql/with-connection
   db
   (sql/transaction 
    (try
      (sql/drop-table :blogs)
      (catch Exception _)))))

(defn create-blogs
  "Create a table to store the blog information"
  []
  (sql/with-connection
   db
   (sql/transaction   
    (sql/create-table
     :blogs
     [:id :int "PRIMARY KEY" "AUTO_INCREMENT"]
     [:title "varchar(255)"]
     [:body "varchar(255)"]))))