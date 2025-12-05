import LatestUpdate from "@/component/last-update";
import PopularToday from "@/component/popular-today";
import SearchBox from "@/component/search-box";
import SerialPopular from "@/component/serial-popular";

export const revalidate = 60 * 5;
export default function Home() {
  const sampleItems = [
    {
      id: "1",
      title: "One Piece",
      url: "/manga/one-piece",
      cover: "https://images.unsplash.com/photo-1614294148950-1b5754074c3a?w=300&h=400&fit=crop",
      type: "Manga",
      isNew: true,
      status: "Ongoing",
      chapters: [
        {
          url: "/manga/one-piece/chapter-1100",
          title: "Chapter 1100: New World",
          timeago: "2 hours ago",
        },
        {
          url: "/manga/one-piece/chapter-1099",
          title: "Chapter 1099: The Final Battle",
          timeago: "1 day ago",
        },
      ],
    },
    {
      id: "2",
      title: "Attack on Titan",
      url: "/manga/attack-on-titan",
      cover: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop",
      type: "Manga",
      isNew: false,
      status: "Completed",
      chapters: [
        {
          url: "/manga/attack-on-titan/chapter-139",
          title: "Chapter 139: Final Chapter",
          timeago: "3 days ago",
        },
      ],
    },
    {
      id: "3",
      title: "Demon Slayer",
      url: "/manga/demon-slayer",
      cover: "https://images.unsplash.com/photo-1518709268805-4e9042af2176?w=300&h=400&fit=crop",
      type: "Manga",
      isNew: true,
      status: "Ongoing",
      chapters: [
        {
          url: "/manga/demon-slayer/chapter-205",
          title: "Chapter 205: Sunrise",
          timeago: "5 hours ago",
        },
        {
          url: "/manga/demon-slayer/chapter-204",
          title: "Chapter 204: Infinity Castle",
          timeago: "2 days ago",
        },
        {
          url: "/manga/demon-slayer/chapter-203",
          title: "Chapter 203: The Final Form",
          timeago: "4 days ago",
        },
      ],
    },
    {
      id: "4",
      title: "My Hero Academia",
      url: "/manga/my-hero-academia",
      cover: "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=300&h=400&fit=crop",
      type: "Manga",
      isNew: false,
      status: "Ongoing",
      chapters: [
        {
          url: "/manga/my-hero-academia/chapter-400",
          title: "Chapter 400: Plus Ultra",
          timeago: "6 hours ago",
        },
        {
          url: "/manga/my-hero-academia/chapter-399",
          title: "Chapter 399: Final Exam",
          timeago: "1 day ago",
        },
      ],
    },
    {
      id: "5",
      title: "Jujutsu Kaisen",
      url: "/manga/jujutsu-kaisen",
      cover: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop",
      type: "Manga",
      isNew: true,
      status: "Ongoing",
      chapters: [
        {
          url: "/manga/jujutsu-kaisen/chapter-250",
          title: "Chapter 250: Domain Expansion",
          timeago: "1 hour ago",
        },
        {
          url: "/manga/jujutsu-kaisen/chapter-249",
          title: "Chapter 249: Cursed Energy",
          timeago: "3 hours ago",
        },
      ],
    },
  ];

  const popularMock: any[] = [
    {
      id: 1,
      url: "/manga/jujutsu-kaisen",
      title: "I Randomly Have A New Career Every Week",
      cover: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop",
      type: "Manhua",
      colored: true,
      chapter: "Chapter 783",
      ratingPct: 70,
      score: 7,
    },
    {
      id: 1,
      url: "/manga/jujutsu-kaisen",
      title: "Magic Emperor",
      cover: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop",
      type: "Manhua",
      colored: true,
      chapter: "Chapter 783",
      ratingPct: 70,
      score: 7,
    },
    {
      id: 1,
      url: "/manga/jujutsu-kaisen",
      title: "Magic Emperor",
      cover: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop",
      type: "Manhua",
      colored: true,
      chapter: "Chapter 783",
      ratingPct: 70,
      score: 7,
    },
    {
      id: 1,
      url: "/manga/jujutsu-kaisen",
      title: "Magic Emperor",
      cover: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop",
      type: "Manhua",
      colored: true,
      chapter: "Chapter 783",
      ratingPct: 70,
      score: 7,
    },
    {
      id: 1,
      url: "/manga/jujutsu-kaisen",
      title: "Magic Emperor",
      cover: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop",
      type: "Manhua",
      colored: true,
      chapter: "Chapter 783",
      ratingPct: 70,
      score: 7,
    },
    {
      id: 1,
      url: "/manga/jujutsu-kaisen",
      title: "Magic Emperor",
      cover: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop",
      type: "Manhua",
      colored: true,
      chapter: "Chapter 783",
      ratingPct: 70,
      score: 7,
    },
    {
      id: 1,
      url: "/manga/jujutsu-kaisen",
      title: "Magic Emperor",
      cover: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop",
      type: "Manhua",
      colored: true,
      chapter: "Chapter 783",
      ratingPct: 70,
      score: 7,
    },
    {
      id: 1,
      url: "/manga/jujutsu-kaisen",
      title: "Magic Emperor",
      cover: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop",
      type: "Manhua",
      colored: true,
      chapter: "Chapter 783",
      ratingPct: 70,
      score: 7,
    },
    {
      id: 1,
      url: "/manga/jujutsu-kaisen",
      title: "Magic Emperor",
      cover: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop",
      type: "Manhua",
      colored: true,
      chapter: "Chapter 783",
      ratingPct: 70,
      score: 7,
    },
    // ... thêm các item tương tự từ HTML bạn gửi
  ];

  return (
    <div className="w-full">
      <div className="max-w-7xl m-[35px_auto] px-13 flex flex-col gap-5">
        <div className="">
          <PopularToday items={popularMock} maxVisible={7} />
        </div>
        <div className="listupd grid grid-cols-1 sm:grid-cols-7 md:grid-cols-17 gap-5">
          {/* LatestUpdate */}
          <div className="col-span-1 sm:col-span-5 md:col-span-12">
            <LatestUpdate items={sampleItems} nextPageUrl="/manga?order=update&page=2" />
          </div>

          {/* SerialPopular */}
          <div className="flex flex-col gap-5 col-span-1 sm:col-span-2 md:col-span-5">
            <SearchBox />
            <SerialPopular />
          </div>
        </div>

      </div>
    </div>
  );
}
