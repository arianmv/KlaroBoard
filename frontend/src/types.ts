export interface User {
    id: number;
    name: string;
    email: string;
}

export interface Task {
    id: number;
    title: string;
    description: string;
    status: 'TO_DO' | 'IN_PROGRESS' | 'DONE'; // This ensures we can only use valid statuses
    assignee?: User; // The '?' makes the assignee optional
}