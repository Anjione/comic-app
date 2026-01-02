import Blog from "@/component/blog";
import Genre from "@/component/genre";
import LatestUpdate from "@/component/last-update";
import PopularToday from "@/component/popular-today";
import Recommendation from "@/component/recommendation";
import SearchBox from "@/component/search-box";
import SerialPopular from "@/component/serial-popular";
import { SAMPLE_GENRES } from "@/type/comic-info";


export interface ChapterItem {
  url: string;
  title: string;
  timeago: string;
}

export interface LatestManga {
  id: string;
  url: string;
  title: string;
  cover: string;
  type: string;
  isNew: boolean;
  status: string;
  chapters: ChapterItem[];
}

interface PopularManga {
  id: number;
  url: string;
  title: string;
  cover: string;
  type: string;
  colored: boolean;
  chapter: string;
  ratingPct: number;
  score: number;
}

export default function Home() {
  const sampleItems: LatestManga[] = [
    {
      id: "1",
      title: "One Piece",
      url: "/manga/one-piece",
      cover: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop",
      type: "Manhua",
      isNew: true,
      status: "Ongoing",
      chapters: [
        {
          url: "/manga/one-piece/chapter-1100",
          title: "Chapter 1100",
          timeago: "2 hours ago",
        },
        {
          url: "/manga/one-piece/chapter-1099",
          title: "Chapter 1099",
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
          title: "Chapter 139",
          timeago: "3 days ago",
        },
      ],
    },
    {
      id: "3",
      title: "Demon Slayer",
      url: "/manga/demon-slayer",
      cover: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop",
      type: "Manhwa",
      isNew: true,
      status: "Ongoing",
      chapters: [
        {
          url: "/manga/demon-slayer/chapter-205",
          title: "Chapter 205",
          timeago: "5 hours ago",
        },
        {
          url: "/manga/demon-slayer/chapter-204",
          title: "Chapter 204",
          timeago: "2 days ago",
        },
        {
          url: "/manga/demon-slayer/chapter-203",
          title: "Chapter 203",
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
          title: "Chapter 400",
          timeago: "6 hours ago",
        },
        {
          url: "/manga/my-hero-academia/chapter-399",
          title: "Chapter 399",
          timeago: "1 day ago",
        },
      ],
    },
    {
      id: "5",
      title: "Jujutsu Kaisen",
      url: "/manga/jujutsu-kaisen",
      cover: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop",
      type: "Manga",
      isNew: true,
      status: "Ongoing",
      chapters: [
        {
          url: "/manga/jujutsu-kaisen/chapter-250",
          title: "Chapter 250",
          timeago: "1 hour ago",
        },
        {
          url: "/manga/jujutsu-kaisen/chapter-249",
          title: "Chapter 249",
          timeago: "3 hours ago",
        },
      ],
    },
    {
      id: "6",
      title: "Jujutsu Kaisen",
      url: "/manga/jujutsu-kaisen",
      cover: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop",
      type: "Manga",
      isNew: true,
      status: "Ongoing",
      chapters: [
        {
          url: "/manga/jujutsu-kaisen/chapter-250",
          title: "Chapter 250",
          timeago: "1 hour ago",
        },
        {
          url: "/manga/jujutsu-kaisen/chapter-249",
          title: "Chapter 249",
          timeago: "3 hours ago",
        },
      ],
    },
  ];

  const popularMock: PopularManga[] = [
    {
      id: 1,
      url: "/manga/jujutsu-kaisen",
      title: "I Randomly Have A New Career Every Week",
      cover: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop",
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
      cover: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop",
      type: "Manga",
      colored: true,
      chapter: "Chapter 783",
      ratingPct: 70,
      score: 7,
    },
    {
      id: 1,
      url: "/manga/jujutsu-kaisen",
      title: "Magic Emperor",
      cover: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop",
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
      cover: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop",
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
      cover: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop",
      type: "Manga",
      colored: false,
      chapter: "Chapter 783",
      ratingPct: 70,
      score: 7,
    },
    {
      id: 1,
      url: "/manga/jujutsu-kaisen",
      title: "Magic Emperor",
      cover: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop",
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
      cover: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop",
      type: "Manhwa",
      colored: true,
      chapter: "Chapter 783",
      ratingPct: 70,
      score: 7,
    },
    {
      id: 1,
      url: "/manga/jujutsu-kaisen",
      title: "Magic Emperor",
      cover: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop",
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
      cover: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300&h=400&fit=crop",
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
      <div className="max-w-7xl m-[35px_auto] mb-[160px] min-[800px]:mb-[35px] min-[800px]:px-5 min-[1226px]:px-12 flex flex-col gap-4">
        <div className="">
          <PopularToday items={popularMock} maxVisible={7} />
        </div>
        <div className="listupd grid grid-cols-1 lg:grid-cols-7 min-[880px]:grid-cols-17 gap-4">
          {/* LatestUpdate */}
          <div className="flex flex-col col-span-1 lg:col-span-5 min-[880px]:col-span-12 gap-4">
            <LatestUpdate items={sampleItems} nextPageUrl="/manga?order=update&page=2" />
            <Recommendation />
            <Blog />
          </div>

          {/* SerialPopular */}
          <div className="flex flex-col gap-5 col-span-1 lg:col-span-2 min-[880px]:col-span-5">
            <SearchBox />
            <SerialPopular />
            <Genre genres={SAMPLE_GENRES} />
          </div>
        </div>

      </div>
    </div>
  );
}
