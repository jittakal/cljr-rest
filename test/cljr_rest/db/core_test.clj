(ns ^{:author "Jitendra Takalkar"
      :doc "Test Core Database"}
  cljr-rest.db.core-test
  (:use clojure.test
        cljr-rest.db.core))

(deftest test-drop-blogs
  (testing "Drop blogs table")
  (is (nil? (drop-blogs))))

;; (deftest test-create-blogs
;;  (testing "Create blogs table"
;;    (is (= 0 (create-blogs)))))
