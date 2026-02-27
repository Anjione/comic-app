// components/AlphabeticalNav.tsx

import Link from 'next/link';

// Danh sách các ký tự cần hiển thị trong thanh điều hướng
const ALPHABET = [
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
    'N', 'O', 'P', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
];

const SPECIAL_CHARS = ['R', 'T', '"', '+', '#'];

export default function AlphabeticalNav() {
    // URL cơ sở (Base path) cho các liên kết. 
    // Thay đổi '/manga' thành đường dẫn thực tế của bạn nếu cần.
    const BASE_PATH = '/manga/list-mode';

    return (
        <div
            id="tsnlistssc"
            className="flex flex-wrap gap-2 px-8 py-4 bg-transparent shadow-inner justify-center"
        >
            {/* Lặp qua danh sách các ký tự đặc biệt theo thứ tự bạn đã cung cấp */}
            {/* Tôi chỉ liệt kê 4 ký tự đặc biệt đầu tiên của bạn, vì các ký tự còn lại đã có trong ALPHABET */}
            {/* Nếu bạn muốn giữ đúng thứ tự và các ký tự đặc biệt: */}

            {SPECIAL_CHARS.map((char) => (
                <Link
                    key={`nav-${char}`}
                    href={`${BASE_PATH}/#${char.replace(/[^a-zA-Z0-9]/g, '')}`} // Xử lý các ký tự đặc biệt như " và +
                    scroll={true} // Giữ nguyên hành vi scroll đến anchor (mỏ neo)
                    className="
                        text-xs w-8 h-8 flex items-center justify-center 
                        bg-[#333] text-[15px] text-gray-200 rounded-xs 
                        hover:bg-black hover:text-white transition-colors duration-150
                    "
                >
                    {char === '#' ? '#' : char}
                </Link>
            ))}

            {/* Thanh điều hướng bảng chữ cái chính */}
            {ALPHABET.map((char) => (
                <Link
                    key={`nav-${char}`}
                    href={`${BASE_PATH}/#${char}`}
                    scroll={true}
                    className="
                        text-xs w-8 h-8 flex items-center justify-center text-[15px]
                        bg-[#333] text-gray-200 rounded-xs 
                        hover:bg-black hover:text-white transition-colors duration-150
                    "
                >
                    {char}
                </Link>
            ))}
        </div>
    );
}

// Cách sử dụng component này trong một trang Next.js:
// import AlphabeticalNav from '@/components/AlphabeticalNav';
// function MyPage() {
//   return (
//     <main>
//       <AlphabeticalNav />
//       {/* ... Nội dung trang với các mục có ID tương ứng (ví dụ: <div id="A">...</div>) ... */}
//     </main>
//   );
// }