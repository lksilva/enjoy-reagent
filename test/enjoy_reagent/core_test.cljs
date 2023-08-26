(ns enjoy-reagent.core-test
  (:require
   [reagent.core :as r]
   [cljs.test :refer [deftest is run-tests]]
   [enjoy-reagent.core :as core]))

;; It's so harder to creating good tests when dealing with side effects with ClojureScrip. So, I'm focus on cover only with simple tests by now.
(ns enjoy-reagent.core-test
  (:require
   [cljs.test :refer [deftest is run-tests]]
   [enjoy-reagent.core :as core]))

(deftest test-calc-area
  (is (= (core/calc-area 5 10) 50)))

(deftest test-diff-area
  (is (= (core/diff-area 100 20) 80)))

(deftest test-blue-box
  (let [blue-box-rendered (r/as-element [core/blue-box {:id "bb1"}])
        on-drag-start (:on-drag-start (second blue-box-rendered))]
    (is (fn? on-drag-start))))

(deftest test-red-box
  (let [visible-area (r/atom 100)
        red-box-rendered (r/as-element [core/red-box visible-area])
        on-drag-over (:on-drag-over (second red-box-rendered))
        on-drop (:on-drop (second red-box-rendered))]
    (is (fn? on-drag-over))
    (is (fn? on-drop))))

(deftest test-current-page
  (let [current-page-rendered (r/as-element [core/current-page])]
    (is (fn? (:component-did-mount current-page-rendered)))
    (is (fn? (:reagent-render current-page-rendered)))))

;; Run the tests
(run-tests)

