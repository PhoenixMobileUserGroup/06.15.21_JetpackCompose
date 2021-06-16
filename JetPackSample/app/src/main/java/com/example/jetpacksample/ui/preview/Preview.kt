package com.example.jetpacksample.ui.preview

import com.example.jetpacksample.ui.model.Todo
import org.joda.time.LocalDate

class Preview {
    companion object {
        val data: List<Todo> = listOf(
            Todo(
                title = "This is my todo",
                description = "An example description that's amazing",
                dueDate = LocalDate.now()
            ),
            Todo(
                title = "Another todo",
                description = "For all the love in the world, get back to work - Your Manager",
                dueDate = LocalDate.now()
            ),
            Todo(
                title = "Do your manager's work",
                description = "Gosh he makes me do all of his work",
                dueDate = LocalDate.now().plusDays(5)
            )
        )
    }
}