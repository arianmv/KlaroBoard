import { useSortable } from '@dnd-kit/sortable';
import { CSS } from '@dnd-kit/utilities';
import type {Task} from '../types';

interface TaskCardProps {
    task: Task;
}

const statusColorMap: { [key in Task['status']]: string } = {
    TO_DO: 'border-l-yellow-500',
    IN_PROGRESS: 'border-l-blue-500',
    DONE: 'border-l-green-500',
};

const TaskCard = ({ task }: TaskCardProps) => {
    const {
        attributes,
        listeners,
        setNodeRef,
        transform,
        transition,
        isDragging,
    } = useSortable({ id: task.id }); // Use the task's ID as the unique identifier

    const style = {
        transform: CSS.Transform.toString(transform),
        transition,
        opacity: isDragging ? 0.5 : 1, // Make the card semi-transparent while dragging
    };

    return (
        <div
            ref={setNodeRef}
            style={style}
            {...attributes}
            {...listeners}
            className={`bg-white p-4 rounded-lg shadow-sm border-l-4 ${statusColorMap[task.status]}`}
        >
            <h3 className="font-bold text-lg text-gray-800">{task.title}</h3>
            <p className="text-gray-600 text-sm mt-1">{task.description}</p>
            {task.assignee && (
                <div className="mt-3">
                    <span className="text-xs font-semibold text-gray-500">Assignee:</span>
                    <p className="text-sm text-gray-700">{task.assignee.name}</p>
                </div>
            )}
        </div>
    );
};

export default TaskCard;