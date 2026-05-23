(ns url-shortener.cli
  (:require [url-shortener.client :as client]))

(defn read-line-prompt [msg]
  (println msg)
  (print "  > ")
  (flush)
  (read-line))

(defn print-menu []
  (println "")
  (println "=== URL Shortener ===")
  (println "1. Создать")
  (println "2. Показать")
  (println "3. Изменить")
  (println "4. Удалить")
  (println "5. Выйти"))

(defn handle-create []
  (let [normal (read-line-prompt "Введите обычный URL для сокращения:")]
    (println "")
    (println "Отправка запроса...")
    (println (str "Ответ: " (client/create-url normal)))))

(defn handle-show []
  (let [short (read-line-prompt "Введите короткий URL:")]
    (println "")
    (println "Отправка запроса...")
    (if-let [result (client/show-url short)]
      (println (str "Ответ: " result))
      (println "Ответ: Ошибка"))))

(defn handle-update []
  (print "Введите короткий URL и обычный URL через пробел: ")
  (flush)
  (let [line (read-line)
        parts (clojure.string/split line #"\s+" 2)]
    (when (= 2 (count parts))
      (let [[short normal] parts]
        (println "")
        (println "Отправка запроса...")
        (if (= 200 (client/update-url short normal))
          (println "Ответ: OK")
          (println "Ответ: Ошибка"))))))

(defn handle-delete []
  (let [short (read-line-prompt "Введите короткий URL:")]
    (println "")
    (println "Отправка запроса...")
    (if (= 200 (client/delete-url short))
      (println "Ответ: OK")
      (println "Ответ: Ошибка"))))

(defn run-cli []
  (println "Клиент URL Shortener")
  (loop []
    (print-menu)
    (print "Выберите действие: ")
    (flush)
    (let [choice (read-line)]
      (case choice
        "1" (handle-create)
        "2" (handle-show)
        "3" (handle-update)
        "4" (handle-delete)
        "5" (println "Выход.")
        (println "Неверный выбор."))
      (when (not= choice "5")
        (recur)))))
