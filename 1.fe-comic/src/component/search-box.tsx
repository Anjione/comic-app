// components/SearchBox.tsx
"use client";
import { useState } from "react";

export default function SearchBox() {
    const [query, setQuery] = useState("");

    return (
        <div>
            <div className="release">
                <h2 className="font-semibold">
                    Cari di sini…
                </h2>
            </div>
            <form
                role="search"
                method="get"
                id="searchform"
                className="bg-[#222] flex flex-col min-[1080px]:flex-row gap-2 py-3 px-10 items-center box-border w-full"
                action="https://komik25.com/"
            >
                <input
                    type="text"
                    id="s"
                    name="s"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    className="h-8 w-full text-[#000] bg-white border border-gray-300 rounded px-3 py-2 focus:outline-none"
                />
                <button
                    type="submit"
                    id="searchsubmit"
                    className="h-8 bg-[#000] text-xs text-white px-4 py-2 rounded flex-shrink-0"
                >
                    Search
                </button>
            </form>


        </div>
    );
}
