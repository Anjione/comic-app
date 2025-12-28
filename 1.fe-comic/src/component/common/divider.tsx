import React from "react";

interface DividerProps {
    children?: React.ReactNode;
    className?: string;
    orientation?: "horizontal" | "vertical";
    labelPosition?: "left" | "center" | "right";
    variant?: "solid" | "dashed" | "dotted";
}

const Divider: React.FC<DividerProps> = ({
    children,
    className = "",
    orientation = "horizontal",
    labelPosition = "center",
    variant = "solid",
}) => {
    const borderStyle =
        variant === "dashed" ? "border-dashed" :
            variant === "dotted" ? "border-dotted" :
                "border-solid";

    if (orientation === "vertical") {
        return (
            <div
                className={`inline-block h-full min-h-[1em] w-[1px] self-stretch bg-neutral-700 opacity-100 ${borderStyle} ${className}`}
            />
        );
    }

    return (
        <div className={`flex items-center my-4 ${className}`}>
            {/* Đường kẻ bên trái */}
            <div
                className={`flex-grow border-t border-[#333] ${borderStyle} ${children && labelPosition === "left" ? "max-w-[20px]" : ""
                    }`}
            ></div>

            {/* Nội dung text ở giữa (nếu có) */}
            {children && (
                <span
                    className={`mx-4 text-sm text-gray-400 font-medium shrink-0 uppercase tracking-wider`}
                >
                    {children}
                </span>
            )}

            {/* Đường kẻ bên phải */}
            <div
                className={`flex-grow border-t border-[#333] ${borderStyle} ${children && labelPosition === "right" ? "max-w-[20px]" : ""
                    }`}
            ></div>
        </div>
    );
};

export default Divider;