import { useState, useEffect } from 'react';
import apiClient from '../api/apiClient';
import type {Task} from '../types';
import Column from './Column';
import { DndContext, type DragEndEvent } from '@dnd-kit/core';

const Board = () => {
    const [tasks, setTasks] = useState<Task[]>([]);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        apiClient.get('/tasks')
            .then(response => {
                setTasks(response.data);
            })
            .catch(err => {
                console.error("Error fetching tasks:", err);
                setError("Failed to fetch tasks. Is the backend server running?");
            });
    }, []);

    const handleDragEnd = (event: DragEndEvent) => {
        const { active, over } = event;

        if (over && active.id !== over.id) {
            const activeTask = tasks.find(t => t.id === active.id);
            const overColumnStatus = over.id as Task['status'];

            if (activeTask && activeTask.status !== overColumnStatus) {
                // Optimistically update the UI
                const updatedTasks = tasks.map(task =>
                    task.id === active.id ? { ...task, status: overColumnStatus } : task
                );
                setTasks(updatedTasks);

                // Persist the change to the backend
                apiClient.put(`/tasks/${active.id}`, {
                    ...activeTask,
                    status: overColumnStatus,
                    assigneeId: activeTask.assignee?.id
                }).catch(err => {
                    console.error("Failed to update task status:", err);
                    setError("Failed to update task. Reverting changes.");
                    // Revert on failure
                    setTasks(tasks);
                });
            }
        }
    };

    const todoTasks = tasks.filter(task => task.status === 'TO_DO');
    const inProgressTasks = tasks.filter(task => task.status === 'IN_PROGRESS');
    const doneTasks = tasks.filter(task => task.status === 'DONE');

    return (
        <DndContext onDragEnd={handleDragEnd}>
            <div className="p-4 sm:p-8">
                <h1 className="text-3xl font-bold mb-6 text-gray-800">KlaroBoard</h1>

                {error && <p className="text-red-500 bg-red-100 p-4 rounded-md">{error}</p>}

                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <Column title="To Do" tasks={todoTasks} status="TO_DO" />
                    <Column title="In Progress" tasks={inProgressTasks} status="IN_PROGRESS" />
                    <Column title="Done" tasks={doneTasks} status="DONE" />
                </div>
            </div>
        </DndContext>
    );
};

export default Board;