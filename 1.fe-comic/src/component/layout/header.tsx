"use client";

import Link from "next/link";
import Image from "next/image";
import { useState, useRef, useEffect } from "react";
import logoSrc from "@/asset/logo_v1.png";

const NAV = [
  { title: "Beranda", href: "/" },
  { title: "Bookmark", href: "/bookmark" },
  { title: "Top Komik", href: "/manga?order=popular" },
  { title: "Genre", href: "/manga?order=" },
];

export default function Header() {
  const [open, setOpen] = useState(false); // mobile menu
  const [query, setQuery] = useState("");
  const [showSearch, setShowSearch] = useState(false); // mobile search toggle
  const inputRef = useRef<HTMLInputElement | null>(null);

  // focus input when mobile search opens
  useEffect(() => {
    if (showSearch) inputRef.current?.focus();
  }, [showSearch]);

  return (
    <div className="w-full th bg-[#000] dark:bg-[#000] shadow-sm">
      <div className="centernav bound max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          {/* left: mobile menu button + logo */}
          <div className="flex items-center gap-3">
            <div className="flex items-center gap-3 m-[5px_20px_5px_0]">
              {/* mobile menu button */}
              <button
                aria-label="Open menu"
                aria-expanded={open}
                onClick={() => setOpen((v) => !v)}
                className="shme p-2 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 md:hidden"
              >
                <i className="fa fa-bars" aria-hidden="true" />
              </button>

              {/* site branding (logo) */}
              <header role="banner" itemScope itemType="http://schema.org/WPHeader">
                <div className="site-branding logox flex items-center gap-3">
                  <Link href="/" title="Komik25.com" className="flex items-center">
                    {/* NOTE: replace `logoSrc` with actual image URL. Using next/image */}
                    <div className="relative w-[157px] h-[50px]">
                      <Image
                        src={logoSrc}
                        alt="Komik25.com"
                        fill
                        style={{ objectFit: "contain" }}
                        // if logo is external, ensure next.config has remotePatterns
                      />
                    </div>
                    <span className="hdl sr-only">Komik25.com</span>
                  </Link>
                </div>
                <meta itemProp="name" content="Komik25.com" />
              </header>
            </div>

            {/* center: main nav (desktop) */}
            <nav
              id="main-menu"
              className="mm hidden md:flex text-align-left md:items-center md:space-x-6"
              aria-label="Main navigation"
              role="navigation"
              itemScope
              itemType="http://schema.org/SiteNavigationElement"
            >
              <ul className="menu flex items-center gap-4">
                {NAV.map((n) => (
                  <li key={n.href} className="menu-item px-[8px]">
                    <Link href={n.href} className="text-sm text-[1rem] font-medium text-white transition-colors duration-300 hover:text-[rgba(255,255,255,0.5)]">
                      <span itemProp="name">{n.title}</span>
                    </Link>
                  </li>
                ))}
              </ul>
            </nav>
          </div>

          {/* right: search / mobile search icon */}
          <div className="flex items-center gap-3">
            {/* desktop search */}
            <div className="searchx hidden md:flex items-center">
              <SearchBox
                query={query}
                setQuery={setQuery}
                inputRef={inputRef}
                onSubmit={() => {
                  // default form submit handled by element below if used on page
                }}
              />
            </div>

            {/* mobile search icon */}
            <button
              className="srcmob md:hidden p-2 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
              aria-label="Open search"
              onClick={() => setShowSearch((v) => !v)}
            >
              {showSearch ? <i className="fas fa-times-circle" /> : <i className="fas fa-search" />}
            </button>
          </div>
        </div>

        {/* Mobile menu panel */}
        {open && (
          <div className="mt-2 md:hidden" role="dialog" aria-modal="false">
            <nav className="bg-white dark:bg-slate-900 rounded-md p-3 shadow border">
              <ul className="flex flex-col gap-2">
                {NAV.map((n) => (
                  <li key={n.href}>
                    <Link
                      href={n.href}
                      className="block px-2 py-2 rounded hover:bg-slate-50 dark:hover:bg-slate-800"
                      onClick={() => setOpen(false)}
                    >
                      {n.title}
                    </Link>
                  </li>
                ))}
              </ul>
            </nav>
          </div>
        )}

        {/* Mobile search input */}
        {showSearch && (
          <div className="mt-3 md:hidden">
            <div className="bg-white dark:bg-slate-900 p-2 rounded-md border shadow">
              <SearchBox
                query={query}
                setQuery={setQuery}
                inputRef={inputRef}
                compact
              />
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

/** SearchBox component — reusable */
function SearchBox({
  query,
  setQuery,
  inputRef,
  onSubmit,
  compact = false,
}: {
  query: string;
  setQuery: (v: string) => void;
  inputRef?: React.RefObject<HTMLInputElement | null>;
  onSubmit?: () => void;
  compact?: boolean;
}) {
  return (
    <form
      action="/"
      method="get"
      className="relative"
      role="search"
      itemScope
      itemType="http://schema.org/SearchAction"
      onSubmit={(e) => {
        // basic submit: allow default behaviour (redirect to /?s=)
        if (onSubmit) onSubmit();
      }}
    >
      <meta itemProp="target" content="https://komik25.com/?s={query}" />
      <input
        id="s"
        name="s"
        ref={inputRef}
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        className="search-live px-3 py-2 pr-10 border-0 rounded-[50px] w-48 md:w-64 text-sm bg-[#16151d] dark:bg-[#16151d] text-slate-900 dark:text-slate-100 placeholder-white focus:placeholder-[#575552] dark:focus:placeholder-[#575552] focus:outline-none"
        type="text"
        placeholder="Search"
        autoComplete="off"
        itemProp="query-input"
        aria-label="Search comics"
      />
      <button
        type="submit"
        id="submit"
        aria-label="Search"
        className="cursor-pointer absolute right-1 top-1/2 -translate-y-1/2 px-3 py-1.5 rounded-md bg-transparent text-sm hover:bg-transparent focus:outline-none"
      >
        <i className="fas fa-search font-bold text-[#575552]" aria-hidden />
      </button>
    </form>
  );
}
 