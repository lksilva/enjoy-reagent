(ns enjoy-reagent.core
  (:require
   [reagent.dom :as rdom]
   [clerk.core :as clerk]))

;; TODO:
;; Show visible area of red box
;; Identify when a blue is on top of red
;; Calculate the difference when a blue box is on top of red box
(defn red-box []
  [:div.red-box
   {:on-drag-over
    (fn [e]
      (.preventDefault e))
    :on-drop
    (fn [e]
      (let [id (-> e .-dataTransfer (.getData "text"))
            element (-> js/document (.getElementById id))
            available-zone (-> e .-target)]
        (.appendChild available-zone element)
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
          ;; TODO: in the drag over movement, set origina color
        (set! (.. e -currentTarget -style -backgroundColor) "#c8dfff")
        (set! (.. e -currentTarget -style -marginBottom) "10px"))}
     ""]))

(defn current-page []
  (fn []
    [:div.container
     [:div.left
      [blue-box {:id "bb1"}]
      [blue-box {:id "bb2"}]
      [blue-box {:id "bb3"}]
      [blue-box {:id "bb4"}]
      [blue-box {:id "bb5"}]]
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
