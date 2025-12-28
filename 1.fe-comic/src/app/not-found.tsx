import Link from 'next/link'

export default function NotFound() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center px-4">
      <div className="text-center">
        <h1 className="text-9xl font-bold text-gray-900  mb-4">
          404
        </h1>
        <h2 className="text-3xl font-semibold text-gray-800 mb-4">
          Trang không tìm thấy
        </h2>
        <p className="text-gray-600 mb-8 max-w-md mx-auto">
          Xin lỗi, trang bạn đang tìm kiếm không tồn tại hoặc đã bị di chuyển.
        </p>
        <Link
          href="/"
          className="inline-block bg-blue-500 hover:bg-blue-600 text-white font-semibold py-3 px-6 transition-colors shadow-md hover:shadow-lg"
        >
          Về trang chủ
        </Link>
      </div>
    </div>
  )
}