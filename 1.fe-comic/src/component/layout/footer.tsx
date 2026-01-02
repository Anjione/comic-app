"use client"
import Link from 'next/link';
import React, { useState, useEffect } from 'react';

// --- Dữ liệu tĩnh cho A-Z List ---
const ALPHABET = [
  { char: '#', param: '.' },
  { char: '0-9', param: '0-9' },
  ...Array.from({ length: 26 }, (_, i) => String.fromCharCode(65 + i)).map(char => ({
    char,
    param: char,
  })),
];
// ---------------------------------

export default function Footer() {

  // Base URL (thay thế bằng đường dẫn chính xác của bạn)
  const BASE_URL = "/az-lists";

  const [showScroll, setShowScroll] = useState(false);

  useEffect(() => {
    const checkScrollTop = () => {
      if (!showScroll && window.scrollY > 400) {
        setShowScroll(true);
      } else if (showScroll && window.scrollY <= 400) {
        setShowScroll(false);
      }
    };

    window.addEventListener('scroll', checkScrollTop);
    return () => {
      window.removeEventListener('scroll', checkScrollTop);
    };
  }, [showScroll]);

  const scrollTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  return (
    // Thay thế div.footercopyright
    <footer className="bg-[#222222] text-gray-400 p-4 pt-0 min-[800px]:pt-6">

      {/* Thay thế div.footer-az */}
      <div className="max-w-7xl mx-auto hidden min-[800px]:block">

        {/* Tiêu đề và Mô tả */}
        <div className="mb-4 flex items-baseline flex-wrap justify-center">
          {/* Phần A-Z LIST (ftaz) */}
          <span className="ftaz text-lg font-bold text-[#999] mr-2">
            A-Z LIST
          </span>

          {/* Dấu gạch đứng (|) */}
          <span className="text-[#999] mr-2 px-2 text-lg">|</span>

          {/* Mô tả (size-s) */}
          <span className="size-s text-xs text-[#999]">
            Searching series order by alphabet name A to Z.
          </span>
        </div>

        {/* Danh sách A-Z (ul.az-list) */}
        {/* Sử dụng flex-wrap để cuộn ngang trên di động nếu cần, hoặc grid để cố định */}
        <ul className="flex flex-wrap justify-center sm:justify-center gap-3 p-0 list-none">
          {ALPHABET.map((item) => (
            <li key={item.char} className="shrink-0">
              <Link
                href={`${BASE_URL}?show=${item.param}`}
                className="
                  block 
                  w-8 h-8 
                  leading-8 
                  text-center 
                  text-[14px] 
                  font-semibold 
                  bg-black text-white
                  hover:text-[#999] 
                  transition-colors 
                  duration-200
                "
              >
                {item.char}
              </Link>
            </li>
          ))}
        </ul>

      </div>

      {/* Clear Div không cần thiết trong Flex/Grid hiện đại, sử dụng margin/padding */}

      {/* Thay thế div.copyright */}
      <div className="mt-0 mb-5 pt-1 min-[800px]:mt-4 min-[800px]:pt-4">
        <div className="txt max-w-7xl mx-auto text-center">
          <p className="indent-5 text-[12.5px] leading-relaxed text-[#a3a3a3]">
            All the comics on this website are only previews of the original comics, there may be many language errors, character names, and story lines. For the original version, please buy the comic if it&apos;s available in your city.
          </p>
        </div>
      </div>

      {/* Button Scroll To Top */}
      <span
        className={`scrollToTop fixed bottom-5 right-5 z-50 cursor-pointer bg-black text-white w-10 h-10 flex items-center justify-center rounded-full shadow-lg transition-opacity duration-300 ${showScroll ? 'opacity-100' : 'opacity-0 pointer-events-none'}`}
        onClick={scrollTop}
      >
        <span className="fas fa-angle-up"></span>
      </span>
    </footer>
  );
}