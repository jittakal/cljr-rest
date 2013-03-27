(ns ^{:author "Jitendra Takalkar"
      :doc "Common functions"}
  cljr-rest.common
  (:use [clojure.tools.logging :only (info error)]))

(defmacro err-handler
  "Macro to log error messages"
  ([code e]
   `(do
      (error ~e (.getMessage ~e))
      {:error-code ~code
       :error-msg (.getMessage ~e)}))
  ([code msg e]
   `(do
      (error ~e ~msg)
      {:error-code ~code
       :error-msg ~msg})))