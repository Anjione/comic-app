import Link from "next/link";

export default function Footer() {
  return (
    <footer className="w-full bg-[#222222] dark:bg-[#222222]">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
        <div className="flex flex-col md:flex-row justify-between items-center gap-4">
          <div className="text-sm text-slate-600 dark:text-slate-400">
            <p>&copy; {new Date().getFullYear()} Komik25.com. All rights reserved.</p>
          </div>
          <nav className="flex flex-wrap gap-4 text-sm">
            <Link href="/" className="text-slate-600 dark:text-slate-400 hover:text-indigo-600 dark:hover:text-indigo-400">
              Beranda
            </Link>
            <Link href="/about" className="text-slate-600 dark:text-slate-400 hover:text-indigo-600 dark:hover:text-indigo-400">
              Tentang Kami
            </Link>
            <Link href="/contact" className="text-slate-600 dark:text-slate-400 hover:text-indigo-600 dark:hover:text-indigo-400">
              Kontak
            </Link>
            <Link href="/privacy" className="text-slate-600 dark:text-slate-400 hover:text-indigo-600 dark:hover:text-indigo-400">
              Privacy Policy
            </Link>
          </nav>
        </div>
      </div>
    </footer>
  );
}

