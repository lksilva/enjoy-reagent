(ns enjoy-reagent.core
  (:require
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rdom]
   [clerk.core :as clerk]))

;; TODO:
;; Move the blue box
;; Be able to drag and drop blue box
;; Show visible area of red box
;; Identify when a blue is on top of red
;; Calculate the difference when a blue box is on top of red box
(defn red-box []
  [:div.red-box])

(defn blue-box []
  (let [xy (reagent/atom {:x 0 :y 0})]
    (fn []
      [:div.blue-box
       {:style {:left (str (:x @xy) "px")
                :top (str (:y @xy) "px")}
        :draggable true}
       "Move"])))

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
