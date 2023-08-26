(ns enjoy-reagent.core
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rdom]
   [clerk.core :as clerk]))

(defn red-box []
  [:div.red-box])

(defn blue-box []
  [:div.blue-box])

(defn current-page []
  (fn []
    [:div.container
     [:div.left
      [blue-box]
      [blue-box]
      [blue-box]
      [blue-box]
      [blue-box]]
     [:div.right
      [red-box]]]))

;; -------------------------
;; Initialize app

(defn mount-root []
  (rdom/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (clerk/initialize!)
  (mount-root))

(defn ^:dev/after-load reload! []
  (mount-root))
