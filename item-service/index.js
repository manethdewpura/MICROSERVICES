const express = require("express");
const app = express();
const PORT = 8081;

app.use(express.json());

let items = ["Book", "Laptop", "Phone"];

app.get("/items", (req, res) => {
    res.json(items);
});

app.post("/items", (req, res) => {
    const { name } = req.body;
    if (!name) return res.status(400).json({ error: "Name required" });
    items.push(name);
    res.status(201).json({ message: "Item added", item: name });
});

app.get("/items/:id", (req, res) => {
    const id = parseInt(req.params.id);
    if (id < 0 || id >= items.length)
        return res.status(404).json({ error: "Not found" });
    res.json(items[id]);
});

app.listen(PORT, () => {
    console.log(`Item service running on port ${PORT}`);
});