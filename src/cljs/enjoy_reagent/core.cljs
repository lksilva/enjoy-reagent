(ns enjoy-reagent.core
  (:require
   [reagent.dom :as rdom]
   [reagent.core :as r]
   [clerk.core :as clerk]))

(defn calc-area
  "Simple area canculation for rectangular shapes"
  [width height]
  (* width height))

(defn initialize-area
  "Calculate and set the value of initial visible area"
  [visible-area]
  (let [container (-> js/document (.getElementById "droppable"))
        width (-> container .getBoundingClientRect .-width)
        height (-> container .getBoundingClientRect .-height)]
    (reset! visible-area (calc-area width height))))

(defn diff-area
  "Define the new red component visible area"
  [total-area item]
  (let [width (-> item .getBoundingClientRect .-width)
        height (-> item .getBoundingClientRect .-height)
        item-area (calc-area width height)]
    (- total-area item-area)))

;; TODO: in the drag over movement, set origina color
(defn red-box [visible-area]
  [:div.red-box
   {:id :droppable
    :on-drag-over
    (fn [e]
      (.preventDefault e))
    :on-drop
    (fn [e]
      (let [id (-> e .-dataTransfer (.getData "text"))
            element (-> js/document (.getElementById id))
            available-zone (-> e .-target)]
        (.appendChild available-zone element)
        (reset! visible-area (diff-area @visible-area element))
        ))}
   ""])

(defn blue-box [{:keys [id]}]
  (fn []
    [:div.blue-box
     {:id id
      :draggable true
      :on-drag-start
      (fn [e]
        (-> e
            .-dataTransfer
            (.setData "text/plain" (-> e .-target .-id)))
        (set! (.. e -currentTarget -style -backgroundColor) "#c8dfff")
        (set! (.. e -currentTarget -style -marginBottom) "5px"))}
     ""]))

(defn current-page []
  (let [visible-area (r/atom 0)]
    (r/create-class
     {:component-did-mount
      (fn []
        (initialize-area visible-area))
      :reagent-render
      (fn []
        [:<>
         [:div.container
          [:div.left
           [blue-box {:id "bb1"}]
           [blue-box {:id "bb2"}]
           [blue-box {:id "bb3"}]
           [blue-box {:id "bb4"}]
           [blue-box {:id "bb5"}]]
          [:div.right
           [red-box visible-area]]]
         [:div.description
          [:span (str "Total visible area: " @visible-area "px")]]])})))

;; -------------------------
;; Initialize app

(defn mount-root []
  (rdom/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (clerk/initialize!)
  (mount-root))

(defn ^:dev/after-load reload! []
  (mount-root))
