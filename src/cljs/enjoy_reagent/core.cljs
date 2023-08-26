(ns enjoy-reagent.core
  (:require
   [reagent.dom :as rdom]
   [reagent.core :as r]
   [clerk.core :as clerk]))

(defn calc-area
  [total-size item]
  (let [item-size (* (-> item .getBoundingClientRect .-width) (-> item .getBoundingClientRect .-height))]
    (- total-size item-size)))

;; TODO:
;; Apply space between the moved blue-box 
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
        (reset! visible-area (calc-area @visible-area element))
        ))}
   ""])
;; (* (-> red-container .getBoundingClientRect .-width) (-> red-container .getBoundingClientRect .-height))
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
          ;; TODO: in the drag over movement, set origina color
        (set! (.. e -currentTarget -style -backgroundColor) "#c8dfff")
        #_(set! (.. e -currentTarget -style -marginBottom) "5px"))}
     ""]))

(defn current-page []
  (let [visible-area (r/atom 0)]
    (r/create-class
     {:component-did-mount
      (fn []
        (let [red-container (-> js/document (.getElementById "droppable"))]
          (reset! visible-area (* (-> red-container .getBoundingClientRect .-width) (-> red-container .getBoundingClientRect .-height)))))
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
