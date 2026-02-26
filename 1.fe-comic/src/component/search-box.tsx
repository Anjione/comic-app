// components/SearchBox.tsx
"use client";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function SearchBox() {
    const [query, setQuery] = useState("");
    const router = useRouter();

    const handleSearch = (e: React.FormEvent) => {
        e.preventDefault();
        if (query.trim()) {
            // Chuyển hướng đến trang home với tham số s
            router.push(`/?s=${encodeURIComponent(query.trim())}`);
        }
    };

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
                className="bg-[#222] flex flex-row min-[1024px]:flex-col min-[1080px]:flex-row gap-2 py-3 px-10 items-center justify-center box-border w-full"
                action="https://komik25.com/"
            >
                <input
                    type="text"
                    id="s"
                    name="s"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    className="h-8 w-[185px] min-[1024px]:w-full text-black bg-white border border-gray-300 rounded px-3 py-2 focus:outline-none"
                />
                <button
                    type="submit"
                    id="searchsubmit"
                    onClick={handleSearch}
                    className="h-8 bg-black text-xs text-white px-4 py-2 rounded shrink-0 cursor-pointer"
                >
                    Search
                </button>
            </form>


        </div>
    );
}
