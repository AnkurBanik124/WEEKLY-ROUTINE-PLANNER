# 🗓️ Weekly Routine Planner (Java Swing)

A clean, interactive **Weekly Routine Planner GUI** built using **Java Swing**.  
It helps users easily organize, view, and save their weekly class or work schedule — with flexible days, customizable times, and automatic text file export.

---

## 🌟 Features

✅ Modern and minimal **GUI-based planner**  
✅ Add work/classes for **any weekday** (Monday–Friday)  
✅ Option to **add Saturday and Sunday** dynamically  
✅ Create **custom time slots** (e.g., 9AM–10AM, 2PM–3PM)  
✅ View your entire weekly schedule in a popup  
✅ Save the routine neatly formatted in `Routine.txt`  
✅ **Clear** or **exit** anytime with one click  
✅ Built using **Java Swing + Collections Framework**

---

## 🧠 How It Works

1. Choose a **day**, enter the **time** (e.g. `10AM-11AM`), and specify the **activity**.  
2. Click **Add Work** → it adds your entry into the weekly table.  
3. Need weekends? Click **Add Saturday** or **Add Sunday** to include them.  
4. Click **Show Routine** to display a summary of all entries.  
5. Click **Save to File** to export the routine to a neat table format in `Routine.txt`.  
6. Use **Clear Routine** to start fresh or **Exit** to close the app.

---

## 🧩 GUI Layout Overview


+-----------------------------------------------------------+
| WEEKLY ROUTINE PLANNER |
+-----------------------------------------------------------+
| Add Work Panel: [Select Day] [Time] [Activity] [Add Work] |
+-----------------------------------------------------------+
| Weekly Routine Table |
| Time | Monday | Tuesday | Wednesday | Thursday | Friday |
+-----------------------------------------------------------+
| [Add Saturday] [Add Sunday] [Show] [Save] [Clear] [Exit] |
+-----------------------------------------------------------+


---

## 🛠️ Technologies Used

- **Language:** Java  
- **Framework:** Swing (JFrame, JTable, JLabel, JPanel, etc.)  
- **Data Structures:** `Map`, `List`, `LinkedHashMap`, `ArrayList`  
- **File Handling:** `BufferedWriter` for saving routines  

---

## 📂 Folder Structure

Weekly-Routine-Planner/
│
├── WeeklyRoutinePlanner.java # Main program file
├── Routine.txt # Auto-generated output file
└── README.md # Project documentation


---

## 🚀 How to Run

1. Clone this repository:
   ```bash
   git clone https://github.com/<your-username>/Weekly-Routine-Planner.git
   cd Weekly-Routine-Planner
   Compile:  javac WeeklyRoutinePlanner.java
   Run:  java WeeklyRoutinePlanner

  



