import { dehydrate, HydrationBoundary, QueryClient } from '@tanstack/react-query';
import axios from 'axios';
import Home from './home'; // Chúng ta sẽ đổi tên file Home cũ thành cái này

export default async function Page({
  searchParams,
}: {
  searchParams: Promise<{ page?: string }>;
}) {
  const { page: pageString } = await searchParams;
  const page = Number(pageString) || 1;
  const queryClient = new QueryClient();

  // Chạy song song tất cả các API
  await Promise.all([
    queryClient.prefetchQuery({
      queryKey: ['popular-manga'],
      queryFn: async () => {
        const response = await axios.get(`${process.env.NEXT_PUBLIC_API_URL}/manga/popular`);
        return response.data.data;
      },
    }),
    queryClient.prefetchQuery({
      queryKey: ['lastest-update', page],
      queryFn: async () => {
        const response = await axios.get(`${process.env.NEXT_PUBLIC_API_URL}/manga/lastUpdate?pageNum=${page}&pageSize=20`);
        return response.data;
      },
    }),
    queryClient.prefetchQuery({
      queryKey: ['genre'],
      queryFn: async () => {
        const response = await axios.get(`${process.env.NEXT_PUBLIC_API_URL}/genre`);
        return response.data;
      },
    }),
    // Bạn có thể thêm SerialPopular vào đây luôn nếu muốn nó cũng có sẵn data
    // queryClient.prefetchQuery({
    //   queryKey: ['serial-popular'],
    //   queryFn: async () => {
    //     const response = await axios.get(`${process.env.NEXT_PUBLIC_API_URL}/manga/popular-serial`);
    //     return response.data.data;
    //   },
    // }),
  ]);

  return (
    <HydrationBoundary state={dehydrate(queryClient)}>
      <Home searchParams={{ page }} />
    </HydrationBoundary>
  );
}