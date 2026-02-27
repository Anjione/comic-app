import React from 'react'

function Star({ type }: { type: 'full' | 'half' | 'empty' }) {
    if (type === 'full') {
        return (
            <svg className="w-4 h-4 text-amber-400" viewBox="0 0 20 20" fill="currentColor">
                <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.955a1 1 0 00.95.69h4.163c.969 0 1.371 1.24.588 1.81l-3.37 2.449a1 1 0 00-.364 1.118l1.287 3.955c.3.92-.755 1.688-1.54 1.118l-3.37-2.449a1 1 0 00-1.176 0l-3.37 2.449c-.784.57-1.84-.197-1.54-1.118l1.287-3.955a1 1 0 00-.364-1.118L2.07 9.382c-.783-.57-.38-1.81.588-1.81h4.163a1 1 0 00.95-.69l1.286-3.955z" />
            </svg>
        );
    } else if (type === 'half') {
        return (
            <svg className="w-4 h-4" viewBox="0 0 20 20">
                <defs>
                    <clipPath id="half-left">
                        <rect x="0" y="0" width="10" height="20" />
                    </clipPath>
                </defs>

                {/* Empty star */}
                <path
                    d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.955a1 1 0 00.95.69h4.163c.969 0 1.371 1.24.588 1.81l-3.37 2.449a1 1 0 00-.364 1.118l1.287 3.955c.3.92-.755 1.688-1.54 1.118l-3.37-2.449a1 1 0 00-1.176 0l-3.37 2.449c-.784.57-1.84-.197-1.54-1.118l1.287-3.955a1 1 0 00-.364-1.118L2.07 9.382c-.783-.57-.38-1.81.588-1.81h4.163a1 1 0 00.95-.69l1.286-3.955z"
                    fill="transparent"
                    stroke="#555b57"
                    strokeWidth="2"
                />

                {/* Half filled */}
                <path
                    d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.955a1 1 0 00.95.69h4.163c.969 0 1.371 1.24.588 1.81l-3.37 2.449a1 1 0 00-.364 1.118l1.287 3.955c.3.92-.755 1.688-1.54 1.118l-3.37-2.449a1 1 0 00-1.176 0l-3.37 2.449c-.784.57-1.84-.197-1.54-1.118l1.287-3.955a1 1 0 00-.364-1.118L2.07 9.382c-.783-.57-.38-1.81.588-1.81h4.163a1 1 0 00.95-.69l1.286-3.955z"
                    fill="#ffc700"
                    strokeWidth="1"
                    clipPath="url(#half-left)"
                />
            </svg>
        );
    } else {
        return (
            <svg className="w-4 h-4" viewBox="0 0 20 20">
                <path
                    d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.955a1 1 0 00.95.69h4.163c.969 0 1.371 1.24.588 1.81l-3.37 2.449a1 1 0 00-.364 1.118l1.287 3.955c.3.92-.755 1.688-1.54 1.118l-3.37-2.449a1 1 0 00-1.176 0l-3.37 2.449c-.784.57-1.84-.197-1.54-1.118l1.287-3.955a1 1 0 00-.364-1.118L2.07 9.382c-.783-.57-.38-1.81.588-1.81h4.163a1 1 0 00.95-.69l1.286-3.955z"
                    fill="transparent"
                    stroke="#555b57"
                    strokeWidth="2"
                />
            </svg>
        );
    }
}

// Chuyển điểm số 0..10 sang 0..5 sao, có half star
function calcStars(score?: number): ('full' | 'half' | 'empty')[] {
    if (typeof score !== 'number') return Array(5).fill('empty');

    const rating = Math.max(0, Math.min(score, 10)); // clamp 0..10
    const fullStars = Math.floor(rating / 2); // số sao đầy
    const halfStar = rating % 2 >= 1 ? 1 : 0; // nửa sao nếu phần dư >=1
    const emptyStars = 5 - fullStars - halfStar;

    const stars: ('full' | 'half' | 'empty')[] = [];

    for (let i = 0; i < fullStars; i++) stars.push('full');
    if (halfStar) stars.push('half');
    for (let i = 0; i < emptyStars; i++) stars.push('empty');

    return stars;
}

export default function StarRating({ score }: { score?: number }) {
    const stars = calcStars(score);

    return (
        <div className="flex">
            {stars.map((type, idx) => (
                <Star key={idx} type={type} />
            ))}
        </div>
    );
}


