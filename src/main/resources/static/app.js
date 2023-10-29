document.addEventListener("DOMContentLoaded", function () {
    const studentCountInput = document.getElementById("studentCount");
    const generateStudentsButton = document.getElementById("generateStudentsButton");
    const loader = document.getElementById("loader");
    const deleteAllStudentsButton = document.getElementById("deleteAllStudentsButton");
    const startJobButton = document.getElementById("startJobButton");
    const chunkSizeInput = document.getElementById("chunkSize");
    const studentCountText = document.getElementById("studentCountText");

    generateStudentsButton.addEventListener("click", onGenerateStudents);
    deleteAllStudentsButton.addEventListener("click", onDeleteAllStudents);
    startJobButton.addEventListener("click", onStartJob);

    // setInterval(updateJobStatus, 1000);

    updateStudentCount();

    function onGenerateStudents(e) {
        e.preventDefault();
        console.log("Generating students");
        const studentCount = studentCountInput.value;
        const url = "http://localhost:8080/students?total=" + studentCount;
        loader.style.display = "block";
        axios.post(url).then(() => {
            loader.style.display = "none";
            updateStudentCount();
        })
    }

    function onDeleteAllStudents(e) {
        e.preventDefault();
        loader.style.display = "block";
        console.log("Deleting all students");
        const url = "http://localhost:8080/students";
        axios.delete(url).then(() => {
            console.log("Deleted all students");
            loader.style.display = "none";
            updateStudentCount();
        })
    }

    function onStartJob(e) {
        e.preventDefault();
        const chunkSize = chunkSizeInput.value;
        loader.style.display = "block";
        console.log("Starting job");
        const url = "http://localhost:8080/students/calculateAvgScore?chunkSize=" + chunkSize;
        axios.post(url).then(() => {
            console.log("Job started");
            loader.style.display = "none";
        })
    }

    function updateStudentCount() {
        const url = "http://localhost:8080/students/count";
        axios.get(url).then((response) => {
            studentCountText.innerText = response.data;
        })
    }

    // function updateJobStatus() {
    //     const url = "http://localhost:8080/runningJobs";
    //     axios.get(url).then((response) => {
    //         console.log(response.data);
    //     })
    // }
});
