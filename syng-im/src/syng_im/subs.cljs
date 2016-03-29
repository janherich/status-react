(ns syng-im.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [register-sub]]
            [syng-im.models.chat :refer [current-chat-id
                                         latest-msg-id]]
            [syng-im.models.messages :refer [get-messages]]))

(register-sub :get-greeting (fn [db _]
                              (reaction
                                (get @db :greeting))))

(register-sub :get-chat-messages
              (fn [db _]
                (let [chat-id    (-> (current-chat-id @db)
                                     (reaction))
                      latest-msg (-> (latest-msg-id @db @chat-id)
                                     (reaction))]
                  ;; latest-msg signals us that a new message has been added
                  (reaction
                    (let [_ @latest-msg]
                      (get-messages @chat-id))))))

(register-sub :get-current-chat-id (fn [db _]
                                     (-> (current-chat-id @db)
                                         (reaction))))

;; -- User data --------------------------------------------------------------
(register-sub
  :get-user-phone-number
  (fn [db _]
    (reaction
      (get @db :user-phone-number))))

(register-sub
  :get-user-identity
  (fn [db _]
    (reaction
      (get @db :user-identity))))

(register-sub
  :get-loading
  (fn [db _]
    (reaction
      (get @db :loading))))

(register-sub
  :get-confirmation-code
  (fn [db _]
    (reaction
      (get @db :confirmation-code))))

(register-sub
  :get-contacts
  (fn [db _]
    (reaction
      (get @db :contacts))))