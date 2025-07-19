# My Automated Exam Monitoring System 🚀

Hey there! Thanks for stopping by my GitHub repo! 👋

This project is something I'm really excited about – it's my dive into building a robust desktop application in Java that simulates and monitors student behavior during online exams. Think of it as a little "brain" that watches for tricky patterns!

## So, What's the Big Idea?

In today's world of online learning, ensuring fair exams is a huge challenge. While full-blown proctoring systems are complex, I wanted to build the analytical core: a system that can take a stream of student activities and intelligently mark anything that looks a bit fishy. This project was my journey into designing that "detection engine" from the ground up, all powered by Java's awesome capabilities.

## What Can It Do? (Key Features)

My system is designed to:

* **Simulate Real-World Activity:** It takes in various "behavior events" (like opening a new browser tab or periods of silence) complete with timestamps, mimicking how data might flow from real monitoring tools.
* **Intelligent Rule Application:** I've built a super flexible rule engine! It allows me to plug in different detection rules without breaking the existing code. This was a huge win for demonstrating OOP's power.
* **Spot Suspicious Behavior:**
    * **Browser Switch Blitz:** It's smart enough to catch if someone's constantly hopping between their exam and other applications – think "too many browser switches in a short time!" I tackled this with a **sliding window algorithm** to keep track of activity bursts.
    * **Ghost Mode Detection:** Ever wonder if someone's just walked away from their screen? My system can track prolonged periods of inactivity by carefully looking at the time gaps between events.
    * **"Who's That?!" Moments:** It can even detect if an "unknown person" (conceptually, from a webcam feed) appears, tracking suspicious unauthorized presence.
* **Clear & Concise Reporting:** All the findings are neatly compiled into a clean, easy-to-read report, breaking down exactly what was detected and by which rule.
* **Friendly Live Log:** I made up a simple JavaFX graphical interface that shows the entire simulation unfold in real-time, making it easy to watch the events and reports come in.
* **Smooth User Experience:** Even with simulated pauses, the UI stays responsive thanks to  use of Java's threading capabilities – no frozen screens here!

## Under the Hood: My Tech & Learning Journey

This project was a fantastic playground for diving deep into some core computer science concepts.

* **Java & OOP: The Backbone:** This whole system is built with robust **Java**, making heavy use of Object-Oriented Programming. I focused on:
    * **Encapsulation:** Keeping each part tidy (like how a `Student` object manages its own events).
    * **Abstraction:** Using the `Rule` interface was a game-changer – it defines *what* a rule does, not *how*, keeping things flexible.
    * **Polymorphism:** This allowed my `MonitoringSystem` to apply all sorts of different rules (`BrowserSwitchRule`, `IdleTimeRule`, etc.) through one elegant loop, without needing to know their specific types. So cool!
    * **Composition:** Building complex objects from simpler ones, like a `Student` having a list of `BehaviorEvent`s.
* **Cracking Algorithms & Data Structures:**
    * I got hands-on with fundamental **data structures** like `ArrayList` (for those chronological event logs) and `HashMap` (for lightning-fast lookups of students and exams).
    * The **sliding window algorithm** in the browser switch detection was a challenging but rewarding part, really pushing my algorithmic thinking. And figuring out how to precisely track idle times involved some neat time-series analysis.
* **Making it Look Good (ish) & Feel Smooth:**
    * I used **JavaFX** to create that simple, clean log viewer. It was a great way to learn about desktop GUI development.
    * Handling simultaneous tasks (like the simulation running while the UI stays interactive) was a perfect opportunity to understand **concurrency** and Java's threading model.
    * I also tweaked around with modern Java features like `java.time` for timestamps, Java Streams for cleaner data processing, and `StringJoiner` for that nicely formatted report output.

## Want to See It in Action?

Here's a quick demo video of the system running! (Remember, I've sped up the `sleep` times in the code for this demo to keep it snappy!)

[![My Exam Monitoring System Demo Thumbnail](https://github.com/sanjucoder-96/Java-Exam-Monitoring-System/blob/65d839c11d0884684aecc871fd593b92eae1581e/images/Output.png)]

*(Replace `PASTE_YOUR_RAW_IMAGE_LINK_HERE` with the raw URL of your app screenshot you uploaded to GitHub.)*
*(Replace `PASTE_YOUR_LINKEDIN_POST_URL_HERE` with the **permalink of your LinkedIn post**.)*

## How to Get It Running on Your Machine

### Prerequisites:
* Make sure you have **Java Development Kit (JDK) 11 or higher** installed.
* You'll also need the **JavaFX SDK** (the version matching your JDK) downloaded and unzipped.

### Running with IntelliJ IDEA:
1.  **Clone this repository:**
    ```bash
    git clone [https://github.com/YOUR_GITHUB_USERNAME/java-exam-monitoring-system.git](https://github.com/sanjucoder-96/Java-Exam-Monitoring-System.git)
    cd Java-Exam-Monitoring-System
    ```
    
## My Vision for the Future (Future Enhancements)

This project is just a starting point, but I'm excited about its potential! Some ideas I have for where it could go next include:

* Building more advanced detection rules (e.g., analyzing keystroke dynamics, detecting copy-paste patterns).
* Connecting it to a real database to store student data, exam configurations, and all those monitoring reports permanently.
* Developing a more interactive GUI for setting up exams and viewing reports, maybe even in real-time.
* Exploring actual integration with sensor APIs (imagine plugging into a webcam for live face detection!).
* Looking into event streaming platforms (like Apache Kafka) for truly real-time, scalable data ingestion.

## Let's Connect!

I really enjoyed building this project and diving deep into Java's capabilities. If you have any feedback, questions feel free to reach out!

Happy coding! ✨

---