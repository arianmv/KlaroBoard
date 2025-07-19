import { SortableContext, verticalListSortingStrategy } from '@dnd-kit/sortable';
import { useDroppable } from '@dnd-kit/core';
import type {Task} from '../types';
import TaskCard from './TaskCard';

interface ColumnProps {
    title: string;
    tasks: Task[];
    status: Task['status'];
}

const Column = ({ title, tasks, status }: ColumnProps) => {
    const { setNodeRef } = useDroppable({ id: status });

    return (
        <div className="bg-gray-200 rounded-lg p-4 w-full">
            <h2 className="text-xl font-bold text-gray-700 mb-4">{title}</h2>

            <SortableContext items={tasks.map(t => t.id)} strategy={verticalListSortingStrategy}>
                <div ref={setNodeRef} className="space-y-4" style={{ minHeight: '200px' }}>
                    {tasks.map(task => (
                        <TaskCard key={task.id} task={task} />
                    ))}
                </div>
            </SortableContext>
        </div>
    );
};

export default Column;