"use client";

import { useState, useRef, useEffect } from "react";
import { useRouter } from "next/navigation"; // Dùng để chuyển trang
import { SAMPLE_GENRES, STATUS_LIST, TYPE_LIST, SORT_LIST } from "@/type/comic-info";

type GenreState = Record<number, 1 | 2>;

export default function QuickFilter({ order }: { order?: string }) {
    const router = useRouter();

    // State quản lý dropdown nào đang mở (genre, status, type, order)
    const [activeDropdown, setActiveDropdown] = useState<string | null>(null);

    // THAY THẾ: Lưu trữ trạng thái 3 chiều: 1 = Included, 2 = Excluded
    const [genreStates, setGenreStates] = useState<GenreState>({});

    // State lưu giá trị người dùng chọn
    const [status, setStatus] = useState("");
    const [type, setType] = useState("");
    const [sortBy, setSortBy] = useState(order || "");

    useEffect(() => {
        setSortBy(order || "");
    }, [order]);

    // Hàm toggle mở/đóng dropdown
    const toggleDropdown = (name: string) => {
        setActiveDropdown(activeDropdown === name ? null : name);
    };

    // Xử lý chọn Genre (Checkbox)
    // --- LOGIC XỬ LÝ 3 TRẠNG THÁI (Tristate Handler) ---
    const handleGenreChange = (id: number) => {
        setGenreStates(prevStates => {
            const currentState = prevStates[id];
            const newState = { ...prevStates };

            if (!currentState) {
                // Trạng thái 0 (Unchecked) -> 1 (Checked)
                newState[id] = 1;
            } else if (currentState === 1) {
                // Trạng thái 1 (Checked) -> 2 (Excluded)
                newState[id] = 2;
            } else {
                // Trạng thái 2 (Excluded) -> 0 (Unchecked/Remove)
                delete newState[id];
            }
            return newState;
        });
    };

    // Hàm tạo query string được cập nhật để xử lý Included/Excluded
    const handleSearch = (e: React.FormEvent) => {
        e.preventDefault();

        const params = new URLSearchParams();
        const includedGenres: number[] = [];
        const excludedGenres: number[] = [];

        // Phân loại Genres thành Included (1) và Excluded (2)
        Object.entries(genreStates).forEach(([idString, state]) => {
            const id = Number(idString);
            if (state === 1) {
                includedGenres.push(id);
            } else if (state === 2) {
                excludedGenres.push(id);
            }
        });

        // Gắn vào Query Params
        if (includedGenres.length > 0) params.append("genre_in", includedGenres.join(',')); // Hoặc dùng "genre[]"
        if (excludedGenres.length > 0) params.append("genre_not", excludedGenres.join(','));

        if (status) params.set("status", status);
        if (type) params.set("type", type);
        if (sortBy) params.set("order", sortBy);

        // Chuyển hướng
        router.push(`/manga?${params.toString()}`);
        setActiveDropdown(null);
    };

    // Hàm đếm số lượng mục đang được lọc (Checked + Excluded)
    const filterCount = Object.keys(genreStates).length;

    // Click ra ngoài thì đóng dropdown (Optional - nâng cao trải nghiệm)
    const dropdownRef = useRef<HTMLDivElement>(null);
    useEffect(() => {
        function handleClickOutside(event: MouseEvent) {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
                setActiveDropdown(null);
            }
        }
        document.addEventListener("mousedown", handleClickOutside);
        return () => document.removeEventListener("mousedown", handleClickOutside);
    }, []);

    return (
        <div className="w-full p-3 rounded-md mb-2 relative" ref={dropdownRef}>
            <form onSubmit={handleSearch} className="flex flex-wrap items-center gap-2">

                {/* --- 1. DROPDOWN GENRE (Phức tạp nhất) --- */}
                <div className="w-[calc(50%-0.5rem)] min-[928px]:w-[20%]">
                    <button
                        type="button"
                        className={`w-full flex justify-center items-center gap-1 px-2 py-1 rounded bg-[#333] text-xs text-gray-200 hover:bg-black/20 transition-colors border ${activeDropdown === 'genre' ? 'border-yellow-500' : 'border-transparent'}`}
                        onClick={() => toggleDropdown("genre")}
                    >
                        Genre <span className="bg-black text-gray-400 text-xs px-1.5 rounded font-bold">{filterCount || "All"}</span>
                        <i className={`fa-solid fa-angle-down transition-transform ${activeDropdown === 'genre' ? 'rotate-180' : ''}`}></i>
                    </button>

                    {/* Menu Genre */}
                    {activeDropdown === "genre" && (
                        <div className="absolute top-full left-0 mx-3 w-[97%] bg-[#333] border border-gray-700 rounded shadow-xl z-50 p-4">
                            {/* ... Nút Chọn/Bỏ Chọn Tất Cả (Tùy chọn) ... */}

                            <ul className="grid grid-cols-2 md:grid-cols-4 gap-2 max-h-[400px] overflow-y-auto custom-scrollbar
                                    [&::-webkit-scrollbar]:w-[5px]
                                    [&::-webkit-scrollbar-track]:bg-[#333]
                                    [&::-webkit-scrollbar-thumb]:bg-[#111]
                                    [&::-webkit-scrollbar-track]:rounded-[2px]
                                    [&::-webkit-scrollbar-thumb]:rounded-[2px]">
                                {SAMPLE_GENRES.map((item) => {
                                    const state = genreStates[item.id] || 0; // 0, 1, hoặc 2
                                    const customClass = state === 1 ? 'is-checked' : state === 2 ? 'is-excluded' : 'is-unchecked';

                                    return (
                                        // Sử dụng thẻ div thay vì input/label truyền thống để dễ styling hơn
                                        <li key={item.id} className="cursor-pointer" onClick={() => handleGenreChange(item.id)}>
                                            <div className={`custom-tristate ${customClass} flex items-center gap-1`}>

                                                {/* Đây là vị trí của icon Font Awesome (::before trong CSS) */}
                                                <span className="checkbox-icon"></span>

                                                <span className="text-gray-300 text-sm hover:text-white truncate">
                                                    {item.name}
                                                </span>
                                            </div>
                                        </li>
                                    );
                                })}
                            </ul>
                        </div>
                    )}
                </div>

                {/* --- 2. DROPDOWN STATUS --- */}
                <FilterDropdown
                    title="Status"
                    active={activeDropdown === "status"}
                    onToggle={() => toggleDropdown("status")}
                    currentValue={status}
                    options={STATUS_LIST}
                    onSelect={setStatus}
                />

                {/* --- 3. DROPDOWN TYPE --- */}
                <FilterDropdown
                    title="Type"
                    active={activeDropdown === "type"}
                    onToggle={() => toggleDropdown("type")}
                    currentValue={type}
                    options={TYPE_LIST}
                    onSelect={setType}
                />

                {/* --- 4. DROPDOWN ORDER BY --- */}
                <FilterDropdown
                    title="Order by"
                    active={activeDropdown === "order"}
                    onToggle={() => toggleDropdown("order")}
                    currentValue={sortBy}
                    options={SORT_LIST}
                    onSelect={setSortBy}
                />

                {/* --- BUTTON SEARCH --- */}
                <button type="submit" className="w-full min-[928px]:w-[16%] bg-black text-white text-xs font-bold px-2 py-1 rounded flex justify-center items-center gap-1 transition-colors border border-black">
                    <i className="fa-solid fa-search"></i> Search
                </button>

            </form>
        </div>
    );
}

// --- Component con: Dropdown Single Select với Custom Checkbox Icon ---
interface Option {
    value: string;
    label: string;
}

interface FilterDropdownProps {
    title: string;
    active: boolean;
    onToggle: () => void;
    currentValue: string; // Single string value
    options: Option[];
    onSelect: (value: string) => void; // Single setter
}

// --- Component con: Dropdown Radio đơn giản (Status, Type, Order) ---
function FilterDropdown({ title, active, onToggle, currentValue, options, onSelect }: FilterDropdownProps) {
    // Tìm label của giá trị hiện tại để hiển thị lên nút
    const selectedLabel = options.find((o: Option) => o.value === currentValue)?.label || "All";

    // Xử lý khi click vào một mục: Single select logic
    const handleItemClick = (value: string) => {
        // Nếu click vào chính mục đang chọn, set về rỗng ("") để mô phỏng "Bỏ chọn"
        // Ngược lại, chọn giá trị mới
        const newValue = currentValue === value ? "" : value;
        onSelect(newValue);
        // onToggle(); // Đóng dropdown sau khi chọn (trải nghiệm Single Select)
    }

    return (
        <div className="relative w-[calc(50%-0.5rem)] min-[928px]:w-[20%]">
            <button
                type="button"
                className={`w-full flex justify-center items-center gap-1 px-1 py-1 rounded text-xs hover:bg-gray-200 hover:text-black transition-colors border border-transparent ${active ? 'bg-gray-200 text-black' : 'bg-[#333] text-gray-200'}`}
                onClick={onToggle}
            >
                {title} <span className="text-gray-400 text-xs px-1">({selectedLabel})</span>
                <i className={`fa-solid fa-angle-down transition-transform ${active ? 'rotate-180' : ''}`}></i>
            </button>

            {active && (
                <div className="absolute top-full left-0 mt-2 w-40 bg-[#333] border border-gray-700 rounded shadow-xl z-50 py-2 
                                [&::-webkit-scrollbar]:w-[5px]
                                [&::-webkit-scrollbar]:h-[5px]
                                [&::-webkit-scrollbar-track]:bg-[#333]
                                [&::-webkit-scrollbar-thumb]:bg-[#111]
                                [&::-webkit-scrollbar-track]:rounded-[2px]
                                [&::-webkit-scrollbar-thumb]:rounded-[2px]">
                    {options.map((opt: Option) => {
                        // Kiểm tra trạng thái: CHỈ checked khi giá trị opt.value trùng với currentValue
                        const isChecked = currentValue === opt.value;

                        return (
                            <li key={opt.value} className="list-none cursor-pointer">
                                <div
                                    className={`custom-tristate ${isChecked ? 'is-checked' : 'is-unchecked'} flex items-center gap-1 px-4 py-2 hover:bg-white/10`}
                                    onClick={() => handleItemClick(opt.value)}
                                >
                                    {/* Icon Font Awesome (được định nghĩa trong globals.css) */}
                                    <span className="checkbox-icon"></span>

                                    <span className={`text-sm ${isChecked ? 'text-yellow-500 font-bold' : 'text-gray-300'}`}>
                                        {opt.label}
                                    </span>
                                </div>
                            </li>
                        );
                    })}
                </div>
            )}
        </div>
    );
}