import React from 'react';

export const CardSkeleton: React.FC = () => {
  return (
    <div className="bg-white dark:bg-slate-800 rounded-2xl border border-slate-200 dark:border-slate-700/60 p-5 shadow-sm animate-pulse space-y-4">
      <div className="flex items-center justify-between">
        <div className="w-12 h-12 bg-slate-200 dark:bg-slate-700 rounded-xl" />
        <div className="w-20 h-6 bg-slate-200 dark:bg-slate-700 rounded-full" />
      </div>
      <div className="space-y-2">
        <div className="w-3/4 h-5 bg-slate-200 dark:bg-slate-700 rounded" />
        <div className="w-1/2 h-4 bg-slate-200 dark:bg-slate-700 rounded" />
      </div>
      <div className="pt-2 border-t border-slate-100 dark:border-slate-700/40 flex justify-between items-center">
        <div className="w-24 h-4 bg-slate-200 dark:bg-slate-700 rounded" />
        <div className="w-20 h-8 bg-slate-200 dark:bg-slate-700 rounded-lg" />
      </div>
    </div>
  );
};

export const TableSkeleton: React.FC<{ rows?: number }> = ({ rows = 5 }) => {
  return (
    <div className="bg-white dark:bg-slate-800 rounded-2xl border border-slate-200 dark:border-slate-700/60 overflow-hidden shadow-sm animate-pulse">
      <div className="h-12 bg-slate-100 dark:bg-slate-700/50 border-b border-slate-200 dark:border-slate-700" />
      <div className="divide-y divide-slate-100 dark:divide-slate-700/40">
        {Array.from({ length: rows }).map((_, i) => (
          <div key={i} className="h-16 px-6 flex items-center justify-between">
            <div className="w-1/4 h-4 bg-slate-200 dark:bg-slate-700 rounded" />
            <div className="w-1/6 h-4 bg-slate-200 dark:bg-slate-700 rounded" />
            <div className="w-1/6 h-6 bg-slate-200 dark:bg-slate-700 rounded-full" />
            <div className="w-1/12 h-8 bg-slate-200 dark:bg-slate-700 rounded-lg" />
          </div>
        ))}
      </div>
    </div>
  );
};
