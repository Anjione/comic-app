"use client"
import React, { useState } from "react";

type Item = {
    id: number;
    title: string;
    description: string;
};

type Tab = {
    id: string;
    label: string;
    items: Item[];
};

const tabsData: Tab[] = [
    {
        id: "tab1",
        label: "Tab 1",
        items: [
            { id: 1, title: "Item 1A", description: "Description 1A" },
            { id: 2, title: "Item 1B", description: "Description 1B" },
            { id: 3, title: "Item 1C", description: "Description 1C" },
        ],
    },
    {
        id: "tab2",
        label: "Tab 2",
        items: [
            { id: 4, title: "Item 2A", description: "Description 2A" },
            { id: 5, title: "Item 2B", description: "Description 2B" },
            { id: 6, title: "Item 2C", description: "Description 2C" },
        ],
    },
    {
        id: "tab3",
        label: "Tab 3",
        items: [
            { id: 7, title: "Item 3A", description: "Description 3A" },
            { id: 8, title: "Item 3B", description: "Description 3B" },
            { id: 9, title: "Item 3C", description: "Description 3C" },
        ],
    },
];

const SerialPopular: React.FC = () => {
    const [activeTab, setActiveTab] = useState("tab1");

    const currentTab = tabsData.find((tab) => tab.id === activeTab);

    return (
        <div>
            <div className="release">
                <h2 className="font-semibold">
                    Serial Popular
                </h2>
            </div>
            <section className="bixbox bg-[#222222] dark:bg-[#222222] shadow">
                <div className="max-w-7xl mx-auto">
                    {/* Tabs */}
                    <div className="flex space-x-2 border-b mb-4">
                        {tabsData.map((tab) => (
                            <button
                                key={tab.id}
                                className={`px-4 py-2 font-medium transition-colors ${activeTab === tab.id
                                    ? "border-b-2 border-blue-500 text-blue-600"
                                    : "text-gray-500 hover:text-gray-700"
                                    }`}
                                onClick={() => setActiveTab(tab.id)}
                            >
                                {tab.label}
                            </button>
                        ))}
                    </div>

                    {/* Tab content */}
                    <div className="grid grid-cols-1 gap-4">
                        {currentTab?.items.map((item) => (
                            <div
                                key={item.id}
                                className="p-4 border-b-1 border-[#312f40]"
                            >
                                <h3 className="text-lg font-semibold mb-2">{item.title}</h3>
                                <p className="text-gray-600">{item.description}</p>
                            </div>
                        ))}
                    </div>
                </div>
            </section>
        </div>

    );
};

export default SerialPopular;
