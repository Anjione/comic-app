import Blog from "@/component/blog";
import Genre from "@/component/genre";
import LatestUpdate from "@/component/last-update";
import PopularToday from "@/component/popular-today";
import Recommendation from "@/component/recommendation";
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
      cover: "https://images.unsplash.com/photo-1518709268805-4e9042af2176?w=300&h=400&fit=crop",
      type: "Manga",
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
      cover: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=300&h=400&fit=crop",
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

  const sampleGenres = [
    { name: 'Action', url: 'https://komik25.com/genres/action/' },
    { name: 'Action Adventure', url: 'https://komik25.com/genres/action-adventure/' },
    { name: 'Adaptation', url: 'https://komik25.com/genres/adaptation/' },
    { name: 'Adult', url: 'https://komik25.com/genres/adult/' },
    { name: 'Adventure', url: 'https://komik25.com/genres/adventure/' },
    { name: 'Adventure Drama', url: 'https://komik25.com/genres/adventure-drama/' },
    { name: 'Comedy', url: 'https://komik25.com/genres/comedy/' },
    { name: 'Cooking', url: 'https://komik25.com/genres/cooking/' },
    { name: 'Crime', url: 'https://komik25.com/genres/crime/' },
    { name: 'Demon', url: 'https://komik25.com/genres/demon/' },
    { name: 'Demons', url: 'https://komik25.com/genres/demons/' },
    { name: 'Drama', url: 'https://komik25.com/genres/drama/' },
    { name: 'Ecchi', url: 'https://komik25.com/genres/ecchi/' },
    { name: 'Fantasy', url: 'https://komik25.com/genres/fantasy/' },
    { name: 'Game', url: 'https://komik25.com/genres/game/' },
    { name: 'Gender Bender', url: 'https://komik25.com/genres/gender-bender/' },
    { name: 'Gore', url: 'https://komik25.com/genres/gore/' },
    { name: 'Harem', url: 'https://komik25.com/genres/harem/' },
    { name: 'Hero', url: 'https://komik25.com/genres/hero/' },
    { name: 'Historical', url: 'https://komik25.com/genres/historical/' },
    { name: 'Horror', url: 'https://komik25.com/genres/horror/' },
    { name: 'Isekai', url: 'https://komik25.com/genres/isekai/' },
    { name: 'Josei', url: 'https://komik25.com/genres/josei/' },
    { name: 'Kombay', url: 'https://komik25.com/genres/kombay/' },
    { name: 'Long Strip', url: 'https://komik25.com/genres/long-strip/' },
    { name: 'Magic', url: 'https://komik25.com/genres/magic/' },
    { name: 'Manhwa', url: 'https://komik25.com/genres/manhwa/' },
    { name: 'Martial Arts', url: 'https://komik25.com/genres/martial-arts/' },
    { name: 'Mature', url: 'https://komik25.com/genres/mature/' },
    { name: 'Mecha', url: 'https://komik25.com/genres/mecha/' },
    { name: 'Medical', url: 'https://komik25.com/genres/medical/' },
    { name: 'Military', url: 'https://komik25.com/genres/military/' },
    { name: 'Monsters', url: 'https://komik25.com/genres/monsters/' },
    { name: 'Music', url: 'https://komik25.com/genres/music/' },
    { name: 'Musical', url: 'https://komik25.com/genres/musical/' },
    { name: 'Mystery', url: 'https://komik25.com/genres/mystery/' },
    { name: 'Police', url: 'https://komik25.com/genres/police/' },
    { name: 'Project', url: 'https://komik25.com/genres/project/' },
    { name: 'Psychological', url: 'https://komik25.com/genres/psychological/' },
    { name: 'Regression', url: 'https://komik25.com/genres/regression/' },
    { name: 'Reincarnation', url: 'https://komik25.com/genres/reincarnation/' },
    { name: 'Reincarnation Seinen', url: 'https://komik25.com/genres/reincarnation-seinen/' },
    { name: 'Returner', url: 'https://komik25.com/genres/returner/' },
    { name: 'Romance', url: 'https://komik25.com/genres/romance/' },
    { name: 'School', url: 'https://komik25.com/genres/school/' },
    { name: 'School life', url: 'https://komik25.com/genres/school-life/' },
    { name: 'Sci-fi', url: 'https://komik25.com/genres/sci-fi/' },
    { name: 'Seinen', url: 'https://komik25.com/genres/seinen/' },
    { name: 'Shotacon', url: 'https://komik25.com/genres/shotacon/' },
    { name: 'Shoujo', url: 'https://komik25.com/genres/shoujo/' },
    { name: 'Shoujo Ai', url: 'https://komik25.com/genres/shoujo-ai/' },
    { name: 'Shounen', url: 'https://komik25.com/genres/shounen/' },
    { name: 'Shounen Ai', url: 'https://komik25.com/genres/shounen-ai/' },
    { name: 'Slice of Life', url: 'https://komik25.com/genres/slice-of-life/' },
    { name: 'Sports', url: 'https://komik25.com/genres/sports/' },
    { name: 'Super Power', url: 'https://komik25.com/genres/super-power/' },
    { name: 'Supernatural', url: 'https://komik25.com/genres/supernatural/' },
    { name: 'Supranatural', url: 'https://komik25.com/genres/supranatural/' },
    { name: 'Survival', url: 'https://komik25.com/genres/survival/' },
    { name: 'System', url: 'https://komik25.com/genres/system/' },
    { name: 'Thriller', url: 'https://komik25.com/genres/thriller/' },
    { name: 'Time Travel', url: 'https://komik25.com/genres/time-travel/' },
    { name: 'Tragedy', url: 'https://komik25.com/genres/tragedy/' },
    { name: 'Vampire', url: 'https://komik25.com/genres/vampire/' },
    { name: 'Vampires', url: 'https://komik25.com/genres/vampires/' },
    { name: 'Villainess', url: 'https://komik25.com/genres/villainess/' },
    { name: 'Web Comic', url: 'https://komik25.com/genres/web-comic/' },
    { name: 'Wuxia', url: 'https://komik25.com/genres/wuxia/' }
  ];

  return (
    <div className="w-full">
      <div className="max-w-7xl m-[35px_auto] px-13 flex flex-col gap-4">
        <div className="">
          <PopularToday items={popularMock} maxVisible={7} />
        </div>
        <div className="listupd grid grid-cols-1 sm:grid-cols-7 md:grid-cols-17 gap-4">
          {/* LatestUpdate */}
          <div className="flex flex-col col-span-1 sm:col-span-5 md:col-span-12 gap-4">
            <LatestUpdate items={sampleItems} nextPageUrl="/manga?order=update&page=2" />
            <Recommendation />
            <Blog />
          </div>

          {/* SerialPopular */}
          <div className="flex flex-col gap-5 col-span-1 sm:col-span-2 md:col-span-5">
            <SearchBox />
            <SerialPopular />
            <Genre genres={sampleGenres} />
          </div>
        </div>

      </div>
    </div>
  );
}
